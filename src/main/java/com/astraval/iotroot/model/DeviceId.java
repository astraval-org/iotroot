package com.astraval.iotroot.model;

import java.io.Serializable;
import java.util.Objects;

public class DeviceId implements Serializable {
    private String mountpoint;
    private String clientId;
    private String username;
    
    public DeviceId() {}
    
    public DeviceId(String mountpoint, String clientId, String username) {
        this.mountpoint = mountpoint;
        this.clientId = clientId;
        this.username = username;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceId deviceId = (DeviceId) o;
        return Objects.equals(mountpoint, deviceId.mountpoint) &&
               Objects.equals(clientId, deviceId.clientId) &&
               Objects.equals(username, deviceId.username);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(mountpoint, clientId, username);
    }
}