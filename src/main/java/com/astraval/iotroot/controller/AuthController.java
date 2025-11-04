package com.astraval.iotroot.controller;

import com.astraval.iotroot.model.User;
import com.astraval.iotroot.service.AuthService;
import com.astraval.iotroot.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

@PostMapping("/register")
public Map<String, Object> register(@RequestBody User user) {
    try {
        User savedUser = userService.register(user);
        return Map.of(
            "success", true,
            "message", "Account created successfully",
            "userId", savedUser.getUserId(),
            "email", savedUser.getEmail()
        );
    } catch (RuntimeException e) {
        return Map.of("error", e.getMessage());
    }
}

@PostMapping("/login")
public Map<String, Object> login(@RequestBody User user) {
    try {
        User authenticatedUser = authService.authenticate(user.getEmail(), user.getPassword());
        if (authenticatedUser != null) {
            String token = authService.generateToken(authenticatedUser);
            return Map.of(
                "success", true, 
                "token", token,
                "userId", authenticatedUser.getUserId(), 
                "email", authenticatedUser.getEmail(),
                "username", authenticatedUser.getUsername() != null ? authenticatedUser.getUsername() : authenticatedUser.getEmail(),
                "message", "Login successful"
            );
        }
        return Map.of("success", false, "message", "Invalid email or password");
    } catch (Exception e) {
        return Map.of("success", false, "message", "Login failed: " + e.getMessage());
    }
}

@PostMapping("/validate")
public Map<String, Object> validateToken(@RequestHeader("Authorization") String authHeader) {
    try {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return authService.validateToken(token);
        }
        return Map.of("valid", false, "message", "No token provided");
    } catch (Exception e) {
        return Map.of("valid", false, "message", "Invalid token");
    }
}

@PostMapping("/google")
public Map<String, Object> googleAuth(@RequestBody Map<String, String> request) {
    try {
        String email = request.get("email");
        String name = request.get("name");
        String googleId = request.get("googleId");
        
        // Check if user exists, if not create new user
        User user = userService.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setUsername(name);
            user.setPassword(""); // No password for Google users
            user = userService.save(user);
        }
        
        String token = authService.generateToken(user);
        return Map.of(
            "success", true,
            "token", token,
            "userId", user.getUserId(),
            "email", user.getEmail(),
            "username", user.getUsername() != null ? user.getUsername() : user.getEmail(),
            "message", "Google login successful"
        );
    } catch (Exception e) {
        return Map.of("success", false, "message", "Google login failed: " + e.getMessage());
    }
}

}
