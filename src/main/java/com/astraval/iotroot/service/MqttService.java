package com.astraval.iotroot.service;

import com.astraval.iotroot.config.MqttConfig;
import com.astraval.iotroot.model.SensorData;
import com.astraval.iotroot.model.Topic;
import com.astraval.iotroot.repo.SensorDataRepository;
import com.astraval.iotroot.repo.TopicRepository;
import com.astraval.iotroot.service.WebSocketService;
import java.time.LocalDateTime;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class MqttService implements MqttCallback {
    
    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);
    
    @Autowired
    private MqttConfig mqttConfig;
    
    @Autowired
    private SensorDataRepository sensorDataRepository;
    
    @Autowired
    private TopicRepository topicRepository;
    
    @Autowired
    private WebSocketService webSocketService;
    
    private MqttClient mqttClient;

    @PostConstruct
    public void init() {
        try {
            // Creates MqttClient with url + clientId.
            mqttClient = new MqttClient(mqttConfig.getUrl(), mqttConfig.getClientId());
            mqttClient.setCallback(this);

            // Sets connection options (username, password, keep-alive, etc.).
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(mqttConfig.getUsername());
            options.setPassword(mqttConfig.getPassword().toCharArray());
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(20);
            // Connects to broker.
            logger.info("Connecting to MQTT broker: {}", mqttConfig.getUrl());
            mqttClient.connect(options);
            
            // If successful → subscribes to the topic from config.
            if (mqttClient.isConnected()) {
                logger.info("Successfully connected to MQTT broker");
                mqttClient.subscribe("ietroot/user/+/+"); // Subscribe to all user device topics
                logger.info("Subscribed to all user device topics: ietroot/user/+/+");
            }
            
        } catch (MqttException e) {
            logger.error("Failed to connect to MQTT broker: {}", e.getMessage());
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.warn("MQTT connection lost: {}", cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        
        // Enhanced logging for terminal display
        System.out.println("\n=== MQTT MESSAGE RECEIVED ===");
        System.out.println("Topic: " + topic);
        System.out.println("Payload: " + payload);
        System.out.println("Timestamp: " + java.time.LocalDateTime.now());
        System.out.println("============================\n");
        
        logger.info("Message received on topic '{}': {}", topic, payload);
        
        // Extract userId from topic (format: ietroot/user/{userId}/{deviceName})
        if (topic.startsWith("ietroot/user/")) {
            String[] parts = topic.split("/");
            if (parts.length >= 4) {
                String userId = parts[2];
                String deviceName = parts[3];
                
                SensorData data = new SensorData();
                data.setUserId(userId);
                data.setTopic(topic);
                data.setMessage(payload);
                
                SensorData savedData = sensorDataRepository.save(data);
                
                // Update topic's last received message
                topicRepository.findByUserIdAndDeviceName(userId, deviceName)
                    .ifPresent(topicEntity -> {
                        topicEntity.setLastReceivedMessage(payload);
                        topicEntity.setLastMessageTimestamp(LocalDateTime.now());
                        topicRepository.save(topicEntity);
                    });
                
                webSocketService.sendSensorData(savedData);
                logger.info("Saved and broadcasted sensor data for user: {} from device: {}", userId, deviceName);
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.debug("Message delivery complete");
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.disconnect();
                logger.info("Disconnected from MQTT broker");
            }
        } catch (MqttException e) {
            logger.error("Error disconnecting from MQTT broker: {}", e.getMessage());
        }
    }
}