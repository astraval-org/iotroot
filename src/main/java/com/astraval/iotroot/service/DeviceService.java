package com.astraval.iotroot.service;

import com.astraval.iotroot.model.Device;
import com.astraval.iotroot.repo.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    
    @Autowired
    private DeviceRepository deviceRepository;
    
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
}