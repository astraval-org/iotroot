package com.astraval.iotroot.controller;

import com.astraval.iotroot.model.Device;
import com.astraval.iotroot.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/devices")
@CrossOrigin(origins = "*")
public class DeviceController {
    
    @Autowired
    private DeviceService deviceService;
    
    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }
    
    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Map<String, String> request) {
        String clientId = request.get("client_id");
        if (clientId == null || clientId.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Device device = deviceService.createDevice(clientId);
        return ResponseEntity.ok(device);
    }
    
    @PutMapping("/{clientId}/auth")
    public ResponseEntity<Device> updateDeviceAuth(@PathVariable String clientId, @RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        Device device = deviceService.updateDeviceAuth(clientId, username, password);
        return ResponseEntity.ok(device);
    }
}