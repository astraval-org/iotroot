package com.astraval.iotroot.controller;

import com.astraval.iotroot.model.UserUsage;
import com.astraval.iotroot.repo.UserUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usage")
public class UsageController {
    
    @Autowired
    private UserUsageRepository userUsageRepository;
    
    @PostMapping("/track")
    public ResponseEntity<?> trackUsage(@RequestBody Map<String, String> request) {
        // Temporarily disabled to prevent concurrency errors
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/favorites/{email}")
    public ResponseEntity<List<UserUsage>> getFavorites(@PathVariable String email) {
        List<UserUsage> favorites = userUsageRepository.findTop7ByEmailOrderByUsageCountDesc(email);
        // Limit to 7 items
        if (favorites.size() > 7) {
            favorites = favorites.subList(0, 7);
        }
        return ResponseEntity.ok(favorites);
    }
}