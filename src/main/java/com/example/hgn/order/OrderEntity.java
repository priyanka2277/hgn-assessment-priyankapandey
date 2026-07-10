package com.example.hgn.order;

import com.example.hgn.Alert.AlertEntity;
import com.example.hgn.device.deviceAssignment.DeviceAssignmentEntity;
import com.example.hgn.order.constant.OrderStatus;
import com.example.hgn.trekgrop.TrekGroupEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ORDER_GUID", unique=true, nullable=false)
    private String id;


    @Column(name ="BOOKING_NUMBER")
    private String bookingNumber;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name ="STATUS", nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order" ,cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<DeviceAssignmentEntity> assignments;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private TrekGroupEntity group;

    @OneToMany(mappedBy = "order", cascade ={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<AlertEntity> alerts;

}
