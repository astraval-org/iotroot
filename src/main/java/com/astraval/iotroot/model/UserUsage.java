package com.astraval.iotroot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_favourite_services")
public class UserUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String sectionId;
    
    @Column(nullable = false)
    private Integer usageCount = 0;
    
    public UserUsage() {}
    
    public UserUsage(String email, String sectionId, Integer usageCount) {
        this.email = email;
        this.sectionId = sectionId;
        this.usageCount = usageCount;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSectionId() { return sectionId; }
    public void setSectionId(String sectionId) { this.sectionId = sectionId; }
    
    public Integer getUsageCount() { return usageCount; }
    public void setUsageCount(Integer usageCount) { this.usageCount = usageCount; }
}