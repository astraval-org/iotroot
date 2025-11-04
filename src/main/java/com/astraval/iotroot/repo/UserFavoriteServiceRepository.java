package com.astraval.iotroot.repo;

import com.astraval.iotroot.model.UserFavoriteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserFavoriteServiceRepository extends JpaRepository<UserFavoriteService, Long> {
    List<UserFavoriteService> findByEmail(String email);
    Optional<UserFavoriteService> findByEmailAndSectionId(String email, String sectionId);
    void deleteByEmailAndSectionId(String email, String sectionId);
}