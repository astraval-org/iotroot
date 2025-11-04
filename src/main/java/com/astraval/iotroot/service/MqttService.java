package com.astraval.iotroot.service;

import com.astraval.iotroot.config.MqttConfig;
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
            options.setCleanSession(mqttConfig.isCleanSession());
            options.setConnectionTimeout(mqttConfig.getConnectionTimeout());
            options.setKeepAliveInterval(mqttConfig.getKeepAlive());
            options.setAutomaticReconnect(mqttConfig.isAutomaticReconnect());
            
            // Connects to broker.
            logger.info("Connecting to MQTT broker: {}", mqttConfig.getUrl());
            mqttClient.connect(options);
            
            // If successful → subscribes to the topic from config.
            if (mqttClient.isConnected()) {
                logger.info("Successfully connected to MQTT broker");
                mqttClient.subscribe(mqttConfig.getTopic());
                logger.info("Subscribed to topic: {}", mqttConfig.getTopic());
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
        logger.info("Message received on topic '{}': {}", topic, payload);
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