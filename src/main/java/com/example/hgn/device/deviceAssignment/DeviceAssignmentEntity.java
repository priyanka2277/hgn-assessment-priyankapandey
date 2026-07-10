package com.example.hgn.device.deviceAssignment;

import com.example.hgn.device.DeviceEntity;
import com.example.hgn.order.OrderEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DeviceAssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="DEVICE_ASSIGNMENT_GUID")
    private String id;

    @Column(name ="ASSIGNED_FROM", nullable = false)
    private LocalDateTime assignedFrom;

    @Column(name ="ASSIGNED_UNTIL")
    private LocalDateTime assignedUntil;

    @ManyToOne
    @JoinColumn(name ="DEVICE_GUID", referencedColumnName = "DEVICE_GUID", nullable=false)
    private DeviceEntity device;

    @ManyToOne
    @JoinColumn(name ="ORDER_GUID", referencedColumnName = "ORDER_GUID", nullable=false)
    private OrderEntity order;






}
