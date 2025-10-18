package com.astraval.iotroot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_data")
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String topic;
    private String message;
    private LocalDateTime timestamp;
    private LocalDateTime serverReceivedAt;

    public SensorData() {
        this.timestamp = LocalDateTime.now();
        this.serverReceivedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public LocalDateTime getServerReceivedAt() { return serverReceivedAt; }
    public void setServerReceivedAt(LocalDateTime serverReceivedAt) { this.serverReceivedAt = serverReceivedAt; }
}