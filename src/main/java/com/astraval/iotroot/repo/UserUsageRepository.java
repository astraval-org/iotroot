package com.astraval.iotroot.repo;

import com.astraval.iotroot.model.UserUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserUsageRepository extends JpaRepository<UserUsage, Long> {
    Optional<UserUsage> findByEmailAndSectionId(String email, String sectionId);
    
    @Query("SELECT u FROM UserUsage u WHERE u.email = ?1 AND u.sectionId != 'overview' ORDER BY u.usageCount DESC")
    List<UserUsage> findTop7ByEmailOrderByUsageCountDesc(String email);
    
    @Modifying
    @Query(value = "INSERT INTO user_favourite_services (email, section_id, usage_count) VALUES (?1, ?2, 1) ON DUPLICATE KEY UPDATE usage_count = usage_count + 1", nativeQuery = true)
    void upsertUsage(String email, String sectionId);
    
    @Modifying
    @Query(value = "DELETE FROM user_favourite_services WHERE email = ?1 AND section_id != 'overview' ORDER BY usage_count ASC LIMIT 1", nativeQuery = true)
    void deleteLeastUsed(String email);
}