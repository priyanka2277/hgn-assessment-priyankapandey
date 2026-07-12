package com.example.hgn.Alert;

import com.example.hgn.Alert.constant.ALERTSTATUS;
import com.example.hgn.coordinator.CoordinatorEntity;
import com.example.hgn.device.DeviceEntity;
import com.example.hgn.order.OrderEntity;
import com.example.hgn.trekgrop.TrekGroupEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AlertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ALERT_GUID")
    private String id;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "LOCAL_DATE_TIME", nullable = false)
    private LocalDateTime deviceTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "ALERT_STATUS")
    private ALERTSTATUS alertStatus;

    @Column(name = "ESCALATED")
    private boolean escalated;

    @Column(name = "RECEIVED_AT")
    private LocalDateTime receivedAt;

    @ManyToOne
    @JoinColumn(name = "DEVICE_GUID", referencedColumnName = "DEVICE_GUID")
    private DeviceEntity device;

    @ManyToOne
    @JoinColumn(name = "ORDER_GUID", referencedColumnName = "ORDER_GUID")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "TREKGROUP_GUID", referencedColumnName = "TREKGROUP_GUID")
    private TrekGroupEntity group;

    @ManyToOne
    @JoinColumn(name = "COORDINATOR_GUID", referencedColumnName = "COORDINATOR_GUID")
    private CoordinatorEntity claimedBy;


}
