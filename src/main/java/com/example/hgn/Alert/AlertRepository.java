package com.example.hgn.Alert;

import com.example.hgn.Alert.constant.ALERTSTATUS;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<AlertEntity, String> {
    Optional<AlertEntity> findFirstByDevice_DeviceIdOrderByDeviceTimestampDesc(
            String deviceId
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from AlertEntity  a where a.id = :id")
    Optional<AlertEntity> findByIdForUpdate(@Param("id") String id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE AlertEntity a
            SET a.escalated = true,
                a.alertStatus = com.example.hgn.Alert.constant.ALERTSTATUS.ESCALATED
            WHERE a.alertStatus = com.example.hgn.Alert.constant.ALERTSTATUS.NEW
            AND a.receivedAt <= :cutoff
            """)
    int escalateStaleAlerts(@Param("cutoff") LocalDateTime cutoff);


}
