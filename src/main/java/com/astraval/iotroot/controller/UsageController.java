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
        
        // Skip tracking for overview
        if ("overview".equals(sectionId)) {
            return ResponseEntity.ok().build();
        }
        
        // Check if item already exists in top 7
        UserUsage existing = userUsageRepository.findByEmailAndSectionId(email, sectionId).orElse(null);
        
        if (existing != null) {
            existing.setUsageCount(existing.getUsageCount() + 1);
            userUsageRepository.save(existing);
        } else {
            // Get current favorites count
            List<UserUsage> currentFavorites = userUsageRepository.findTop7ByEmailOrderByUsageCountDesc(email);
            
            if (currentFavorites.size() < 7) {
                // Add new item if less than 7
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
        List<UserUsage> favorites = userUsageRepository.findTop7ByEmailOrderByUsageCountDesc(email);
        // Limit to 7 items
        if (favorites.size() > 7) {
            favorites = favorites.subList(0, 7);
        }
        return ResponseEntity.ok(favorites);
    }
}