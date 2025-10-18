package com.astraval.iotroot.service;

import com.astraval.iotroot.model.User;
import com.astraval.iotroot.repo.UserRepository;
import com.astraval.iotroot.event.UserCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repo;
    private final ApplicationEventPublisher eventPublisher;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repo, ApplicationEventPublisher eventPublisher) {
        this.repo = repo;
        this.eventPublisher = eventPublisher;
    }

    public User register(User user) {
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setUserId("user" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            user.setUsername(user.getEmail());
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = repo.save(user);
        eventPublisher.publishEvent(new UserCreatedEvent(this, user.getUserId()));
        return savedUser;
    }

    public List<User> getAll() {
        return repo.findAll();
    }
public boolean validate(String email, String rawPassword) {
    return repo.findByEmail(email)
               .map(u -> encoder.matches(rawPassword, u.getPassword()))
               .orElse(false);
    }

    public String validateAndGetUserId(String email, String rawPassword) {
        return repo.findByEmail(email)
                   .filter(u -> encoder.matches(rawPassword, u.getPassword()))
                   .map(User::getUserId)
                   .orElse(null);
    }
    
    public User validateAndGetUser(String email, String rawPassword) {
        return repo.findByEmail(email)
                   .filter(u -> encoder.matches(rawPassword, u.getPassword()))
                   .orElse(null);
    }
}
