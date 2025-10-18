package com.astraval.iotroot.repo;

import com.astraval.iotroot.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByUserId(String userId);
    Optional<Topic> findByUserIdAndDeviceName(String userId, String deviceName);
}