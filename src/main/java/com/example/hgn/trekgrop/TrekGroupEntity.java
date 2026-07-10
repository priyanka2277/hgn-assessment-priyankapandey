package com.example.hgn.trekgrop;

import com.example.hgn.Alert.AlertEntity;
import com.example.hgn.order.OrderEntity;
import com.example.hgn.trekgrop.Trekker.TrekkerEntity;
import com.example.hgn.trekgrop.constant.TrekGroupType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TrekGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "TREKGROUP_GUID")
    private String id;

    @Column(name ="GROUP_NAME")
    private String groupName;

    @Enumerated(EnumType.STRING)
    @Column(name ="TREK_GROUP_TYPE")
    private TrekGroupType trekGroupType;

    @Column(name ="NUMBER_OF_PEOPLE_IN_TREK")
    private Integer numberOfPeopleInTrek;

    @OneToOne
    @JoinColumn(name ="ORDER_GUID")
    private OrderEntity order;

    @OneToMany(mappedBy = "group", cascade ={CascadeType.PERSIST,CascadeType.MERGE})
    private List<TrekkerEntity> trekkerList;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<AlertEntity> alerts;

}
