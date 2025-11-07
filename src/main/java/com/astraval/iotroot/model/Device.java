package com.astraval.iotroot.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "vmq_auth_acl")
@IdClass(DeviceId.class)
public class Device {
    
    @Id
    @Column(name = "mountpoint", length = 10)
    private String mountpoint = "";
    
    @Id
    @Column(name = "client_id", length = 128, nullable = false)
    @JsonProperty("client_id")
    private String clientId;
    
    @Id
    @Column(name = "username", length = 128)
    private String username = "";
    
    @Column(name = "password", length = 128)
    private String password;
    
    @Column(name = "publish_acl", columnDefinition = "json")
    private String publishAcl;
    
    @Column(name = "subscribe_acl", columnDefinition = "json")
    private String subscribeAcl;
    
    public Device() {}
    
    public Device(String clientId) {
        this.clientId = clientId;
        this.mountpoint = "";
        this.username = "";
    }
    
    // Getters and setters
    public String getMountpoint() { return mountpoint; }
    public void setMountpoint(String mountpoint) { this.mountpoint = mountpoint; }
    
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getPublishAcl() { return publishAcl; }
    public void setPublishAcl(String publishAcl) { this.publishAcl = publishAcl; }
    
    public String getSubscribeAcl() { return subscribeAcl; }
    public void setSubscribeAcl(String subscribeAcl) { this.subscribeAcl = subscribeAcl; }
}