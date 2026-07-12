package com.example.hgn.trekgrop.Trekker;

import com.example.hgn.trekgrop.TrekGroupEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrekkerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "TREKKER_GUID")
    private String id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "TREKGROUP_GUID")
    private TrekGroupEntity group;

}
