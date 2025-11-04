package com.astraval.iotroot.controller;

import com.astraval.iotroot.model.UserFavoriteService;
import com.astraval.iotroot.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class UserFavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    @GetMapping("/{email}")
    public ResponseEntity<List<UserFavoriteService>> getFavorites(@PathVariable String email) {
        List<UserFavoriteService> favorites = favoriteService.getFavorites(email);
        return ResponseEntity.ok(favorites);
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> addFavorite(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String sectionId = request.get("sectionId");
        
        if (email == null || sectionId == null) {
            return ResponseEntity.badRequest().body("Email and sectionId are required");
        }
        
        favoriteService.addFavorite(email, sectionId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFavorite(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String sectionId = request.get("sectionId");
        
        if (email == null || sectionId == null) {
            return ResponseEntity.badRequest().body("Email and sectionId are required");
        }
        
        favoriteService.removeFavorite(email, sectionId);
        return ResponseEntity.ok().build();
    }
    
    @RequestMapping(method = RequestMethod.OPTIONS, value = "/**")
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }
}