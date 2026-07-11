package com.example.hgn.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    @Query("""
            SELECT o from OrderEntity o 
                        WHERE o.device.deviceId = :deviceId
                        AND o.startDate  <= :endDate
                        AND o.endDate >= :startDate
            
            """)
    List<OrderEntity> findOverlappingOrders(
            @Param("deviceId") String deviceId,
            @Param("startDate")LocalDate startDate,
            @Param("endDate")LocalDate endDate
            );
    Optional<OrderEntity> findByBookingNumber(String bookingNumber);

    @Query("""
                SELECT o FROM OrderEntity o
                WHERE o.device.deviceId = :deviceId
                AND o.id <> :excludeOrderId
                AND o.startDate <= :endDate
                AND o.endDate >= :startDate
            """)
    List<OrderEntity> findOverlappingOrdersExcludingSelf(
            @Param("deviceId") String deviceId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("excludeOrderId") String excludeOrderId
    );
}
