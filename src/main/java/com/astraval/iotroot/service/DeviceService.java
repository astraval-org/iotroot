package com.astraval.iotroot.service;

import com.astraval.iotroot.model.Device;
import com.astraval.iotroot.model.DeviceId;
import com.astraval.iotroot.repo.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    
    @Autowired
    private DeviceRepository deviceRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
    
    public Device createDevice(String clientId) {
        Device device = new Device();
        device.setMountpoint("");
        device.setClientId(clientId);
        device.setUsername("");
        device.setPublishAcl(null);
        device.setSubscribeAcl(null);
        return deviceRepository.save(device);
    }
    
    public Device updateDeviceAuth(String clientId, String username, String password) {
        List<Device> existingDevices = deviceRepository.findByClientId(clientId);
        
        for (Device existing : existingDevices) {
            deviceRepository.delete(existing);
        }
        
        Device device = new Device();
        device.setMountpoint("");
        device.setClientId(clientId);
        device.setUsername(username);
        device.setPassword(passwordEncoder.encode(password));
        device.setPublishAcl(null);
        device.setSubscribeAcl(null);
        
        return deviceRepository.save(device);
    }
}