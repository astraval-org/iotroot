package com.astraval.iotroot.service;

import com.astraval.iotroot.model.SensorData;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendSensorData(SensorData sensorData) {
        messagingTemplate.convertAndSend("/topic/sensor-data/" + sensorData.getUserId(), sensorData);
    }
}