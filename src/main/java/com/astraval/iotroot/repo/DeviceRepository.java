package com.astraval.iotroot.repo;

import com.astraval.iotroot.model.Device;
import com.astraval.iotroot.model.DeviceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, DeviceId> {
    
    @Query("SELECT d FROM Device d WHERE d.clientId = :clientId")
    List<Device> findByClientId(@Param("clientId") String clientId);
}