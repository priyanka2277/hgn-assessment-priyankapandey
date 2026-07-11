package com.example.hgn.device;

import com.example.hgn.Alert.AlertEntity;
import com.example.hgn.device.deviceAssignment.DeviceAssignmentEntity;
import com.example.hgn.order.OrderEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Entity
@Getter
@Setter
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "DEVICE_GUID")
    private String id;

    @Column(name = "DEVICE_ID", nullable = false, unique = true)
    private String deviceId;

    @Column(name = "DEVICE_NAME")
    private String deviceName;

    @OneToMany(mappedBy = "device", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<DeviceAssignmentEntity> assignmentList;

    @OneToMany(mappedBy = "device", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<AlertEntity> alerts;

    @OneToMany(mappedBy = "device", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

}
