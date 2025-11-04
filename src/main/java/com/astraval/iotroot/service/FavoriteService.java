package com.astraval.iotroot.service;

import com.astraval.iotroot.model.UserFavoriteService;
import com.astraval.iotroot.repo.UserFavoriteServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FavoriteService {
    
    @Autowired
    private UserFavoriteServiceRepository repository;
    
    public List<UserFavoriteService> getFavorites(String email) {
        return repository.findByEmail(email);
    }
    
    @Transactional
    public void addFavorite(String email, String sectionId) {
        if (repository.findByEmailAndSectionId(email, sectionId).isEmpty()) {
            UserFavoriteService favorite = new UserFavoriteService();
            favorite.setEmail(email);
            favorite.setSectionId(sectionId);
            repository.save(favorite);
        }
    }
    
    @Transactional
    public void removeFavorite(String email, String sectionId) {
        repository.deleteByEmailAndSectionId(email, sectionId);
    }
}