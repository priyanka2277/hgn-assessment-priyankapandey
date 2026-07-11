package com.example.hgn.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, String> {
    boolean existsByDeviceId(String deviceId);


    Optional<DeviceEntity> findByDeviceId(String s);

    @Query("""
                  select d from DeviceEntity  d 
                  WHERE (:deviceName IS NULL OR LOWER (d.deviceName) = LOWER(:deviceName))
            """)
    Page<DeviceEntity> findAllDevices(
            @Param("deviceName") String deviceName,
            Pageable pageable
    );
}
