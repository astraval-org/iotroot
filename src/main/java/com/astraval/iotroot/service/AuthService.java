package com.astraval.iotroot.service;

import com.astraval.iotroot.model.User;
import com.astraval.iotroot.util.JwtUtil;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public String login(String email, String password) {
        return userService.validateAndGetUserId(email, password);
    }
    
    public User authenticate(String email, String password) {
        return userService.validateAndGetUser(email, password);
    }

    public String generateToken(User user) {
        return jwtUtil.generateToken(
            user.getEmail(),
            user.getUserId(),
            user.getUsername() != null ? user.getUsername() : user.getEmail()
        );
    }

    public Map<String, Object> validateToken(String token) {
        try {
            String email = jwtUtil.extractEmail(token);
            String userId = jwtUtil.extractUserId(token);
            
            if (jwtUtil.validateToken(token, email)) {
                return Map.of(
                    "valid", true,
                    "email", email,
                    "userId", userId
                );
            }
            return Map.of("valid", false, "message", "Token expired or invalid");
        } catch (Exception e) {
            return Map.of("valid", false, "message", "Invalid token format");
        }
    }
}
