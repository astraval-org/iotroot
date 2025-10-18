package com.astraval.iotroot.repo;

import com.astraval.iotroot.model.UserUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserUsageRepository extends JpaRepository<UserUsage, Long> {
    Optional<UserUsage> findByEmailAndSectionId(String email, String sectionId);
    
    @Query("SELECT u FROM UserUsage u WHERE u.email = ?1 AND u.sectionId != 'overview' ORDER BY u.usageCount DESC")
    List<UserUsage> findTop7ByEmailOrderByUsageCountDesc(String email);
}