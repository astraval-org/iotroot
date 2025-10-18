package com.astraval.iotroot.service;

import com.astraval.iotroot.model.Topic;
import com.astraval.iotroot.repo.TopicRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> getUserTopics(String userId) {
        return topicRepository.findByUserId(userId);
    }

    public String createDeviceTopic(String userId, String deviceName) {
        if (topicRepository.findByUserIdAndDeviceName(userId, deviceName).isPresent()) {
            throw new RuntimeException("Device name already exists");
        }
        
        String topicName = "ietroot/user/" + userId + "/" + deviceName;
        Topic topic = new Topic();
        topic.setUserId(userId);
        topic.setDeviceName(deviceName);
        topic.setTopicName(topicName);
        topicRepository.save(topic);
        return topicName;
    }
}