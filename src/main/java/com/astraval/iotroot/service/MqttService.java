package com.astraval.iotroot.service;

import com.astraval.iotroot.config.MqttConfig;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class MqttService implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    @Autowired
    private MqttConfig mqttConfig;

    private MqttClient mqttClient;
    private MqttConnectOptions options;

    @PostConstruct
    public void init() {
        connectAndSubscribe();
    }

    /**
     * Handles connecting and subscribing logic with retries.
     */
    private synchronized void connectAndSubscribe() {
        try {
            if (mqttClient == null) {
                mqttClient = new MqttClient(mqttConfig.getUrl(), mqttConfig.getClientId());
                mqttClient.setCallback(this);
            }

            if (options == null) {
                options = new MqttConnectOptions();
                options.setUserName(mqttConfig.getUsername());
                options.setPassword(mqttConfig.getPassword().toCharArray());
                options.setCleanSession(mqttConfig.isCleanSession());
                options.setConnectionTimeout(mqttConfig.getConnectionTimeout());
                options.setKeepAliveInterval(mqttConfig.getKeepAlive());
                options.setAutomaticReconnect(true); // ✅ enable built-in reconnect
            }

            if (!mqttClient.isConnected()) {
                logger.info("Connecting to MQTT broker: {}", mqttConfig.getUrl());
                mqttClient.connect(options);
                logger.info("Connected to MQTT broker");

                mqttClient.subscribe(mqttConfig.getTopic(), 1);
                logger.info("Subscribed to topic: {}", mqttConfig.getTopic());
            }
        } catch (MqttException e) {
            logger.error("Failed to connect or subscribe to MQTT broker: {}", e.getMessage());
            scheduleReconnect();
        }
    }

    /**
     * Attempt reconnection if connection lost.
     */
    private void scheduleReconnect() {
        new Thread(() -> {
            try {
                Thread.sleep(5000); // wait 5s before retry
                logger.info("Reconnecting to MQTT broker...");
                connectAndSubscribe();
            } catch (InterruptedException ignored) {}
        }).start();
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.warn("MQTT connection lost: {}", cause.getMessage());
        scheduleReconnect();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
        logger.info("Message received on topic '{}': {}", topic, payload);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.debug("Message delivery complete");
    }

    /**
     * Periodic check to ensure connection health.
     */
    @Scheduled(fixedDelay = 30000)
    public void ensureConnected() {
        if (mqttClient == null || !mqttClient.isConnected()) {
            logger.warn("MQTT client disconnected — reconnecting...");
            connectAndSubscribe();
        }
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
