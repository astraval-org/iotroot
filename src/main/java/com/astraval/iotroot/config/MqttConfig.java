package com.astraval.iotroot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mqtt.broker")
public class MqttConfig {
    private String url;
    private String username;
    private String password;
    private String clientId;
    private String topic;

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
}