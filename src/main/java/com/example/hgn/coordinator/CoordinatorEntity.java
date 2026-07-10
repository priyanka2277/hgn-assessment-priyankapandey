package com.example.hgn.coordinator;

import com.example.hgn.Alert.AlertEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CoordinatorEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name ="COORDINATOR_GUID")
    private String id;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name ="LAST_NAME")
    private String lastName;

    @Column(name ="EMAIL" , unique = true)
    private String email;

    @Column(name ="PHONE_NUMBER")
    private String phoneNumber;

    @OneToMany(mappedBy = "claimedBy", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<AlertEntity> alerts;

}
