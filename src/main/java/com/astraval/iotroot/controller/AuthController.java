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

}
