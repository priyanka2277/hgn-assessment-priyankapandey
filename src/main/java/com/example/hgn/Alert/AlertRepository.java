package com.example.hgn.Alert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<AlertEntity, String> {
    Optional<AlertEntity> findFirstByDevice_DeviceIdOrderByDeviceTimestampDesc(
            String deviceId
    );
}
