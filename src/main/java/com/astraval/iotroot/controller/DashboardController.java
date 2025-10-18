package com.astraval.iotroot.controller;

import com.astraval.iotroot.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/stats")
    public Map<String, Object> getDashboardStats() {
        return Map.of(
            "totalUsers", userService.getAll().size(),
            "message", "Welcome to Dashboard"
        );
    }
}