package com.hobak.happinessql.domain.record.domain;

import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.global.infra.database.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private int locationId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = true)
    private String district;

    @Column(nullable = false)
    private String xPos;

    @Column(nullable = false)
    private String yPos;

    @OneToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @Builder
    public Location(int locationId, String fullName, String country, String city, String district, String xPos, String yPos) {
        this.locationId = locationId;
        this.fullName = fullName;
        this.country = country;
        this.city = city;
        this.district = district;
        this.xPos = xPos;
        this.yPos = yPos;
    }
}