package com.astraval.iotroot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String deviceName;

    @Column(nullable = false)
    private String topicName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String lastReceivedMessage;

    private LocalDateTime lastMessageTimestamp;

    public Topic() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getLastReceivedMessage() { return lastReceivedMessage; }
    public void setLastReceivedMessage(String lastReceivedMessage) { this.lastReceivedMessage = lastReceivedMessage; }

    public LocalDateTime getLastMessageTimestamp() { return lastMessageTimestamp; }
    public void setLastMessageTimestamp(LocalDateTime lastMessageTimestamp) { this.lastMessageTimestamp = lastMessageTimestamp; }
}