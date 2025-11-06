package com.astraval.iotroot.repo;

import com.astraval.iotroot.model.Device;
import com.astraval.iotroot.model.DeviceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, DeviceId> {
}