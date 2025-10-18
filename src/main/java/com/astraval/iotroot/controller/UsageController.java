package com.astraval.iotroot.controller;

import com.astraval.iotroot.model.UserUsage;
import com.astraval.iotroot.repo.UserUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        String email = request.get("email");
        String sectionId = request.get("sectionId");
        
        // Check if item already exists in top 3
        UserUsage existing = userUsageRepository.findByEmailAndSectionId(email, sectionId).orElse(null);
        
        if (existing != null) {
            existing.setUsageCount(existing.getUsageCount() + 1);
            userUsageRepository.save(existing);
        } else {
            // Get current favorites count
            List<UserUsage> currentFavorites = userUsageRepository.findTop3ByEmailOrderByUsageCountDesc(email);
            
            if (currentFavorites.size() < 3) {
                // Add new item if less than 3
                userUsageRepository.save(new UserUsage(email, sectionId, 1));
            } else {
                // Replace least used item
                UserUsage leastUsed = currentFavorites.get(currentFavorites.size() - 1);
                userUsageRepository.delete(leastUsed);
                userUsageRepository.save(new UserUsage(email, sectionId, 1));
            }
        }
        
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/favorites/{email}")
    public ResponseEntity<List<UserUsage>> getFavorites(@PathVariable String email) {
        List<UserUsage> favorites = userUsageRepository.findTop3ByEmailOrderByUsageCountDesc(email);
        return ResponseEntity.ok(favorites);
    }
}