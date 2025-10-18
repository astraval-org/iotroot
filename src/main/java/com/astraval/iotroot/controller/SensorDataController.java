package com.astraval.iotroot.controller;

import com.astraval.iotroot.model.SensorData;
import com.astraval.iotroot.repo.SensorDataRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sensor-data")
public class SensorDataController {
    private final SensorDataRepository sensorDataRepository;

    public SensorDataController(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    @GetMapping("/{userId}")
    public List<SensorData> getUserSensorData(@PathVariable String userId) {
        return sensorDataRepository.findByUserIdOrderByTimestampDesc(userId);
    }
}