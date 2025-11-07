package com.astraval.iotroot.repo;

import com.astraval.iotroot.model.Device;
import com.astraval.iotroot.model.DeviceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, DeviceId> {
    
    @Query("SELECT d FROM Device d WHERE d.clientId = :clientId")
    List<Device> findByClientId(@Param("clientId") String clientId);
    
    @Query(value = "UPDATE vmq_auth_acl SET publish_acl = CAST(:publishJson AS json), subscribe_acl = CAST(:subscribeJson AS json) WHERE client_id = :clientId", nativeQuery = true)
    @Modifying
    @Transactional
    void updateDeviceTopics(@Param("clientId") String clientId, @Param("publishJson") String publishJson, @Param("subscribeJson") String subscribeJson);
}