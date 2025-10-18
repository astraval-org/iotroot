package com.astraval.iotroot.controller;

import com.astraval.iotroot.model.Topic;
import com.astraval.iotroot.service.TopicService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/{userId}")
    public List<Topic> getUserTopics(@PathVariable String userId) {
        return topicService.getUserTopics(userId);
    }

    @PostMapping("/{userId}")
    public Map<String, Object> createDeviceTopic(@PathVariable String userId, @RequestBody Map<String, String> request) {
        try {
            String deviceName = request.get("deviceName");
            String topic = topicService.createDeviceTopic(userId, deviceName);
            return Map.of("success", true, "topic", topic, "deviceName", deviceName);
        } catch (RuntimeException e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }
}