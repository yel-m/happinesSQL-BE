package com.hobak.happinessql.domain.record.domain;

import com.hobak.happinessql.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private Double xPos;

    @Column(nullable = false)
    private Double yPos;

    @OneToOne
    @JoinColumn(name = "record_id")
    private Record record;

    @Builder
    public Location(Long locationId, String fullName, String country, String city, String district, Double xPos, Double yPos) {
        this.locationId = locationId;
        this.fullName = fullName;
        this.country = country;
        this.city = city;
        this.district = district;
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
