package com.hobak.happinessql.domain.record.domain;

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
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @Builder
    public Location(String fullName, String country, String city, String district, Double xPos, Double yPos, Record record) {
        this.fullName = fullName;
        this.country = country;
        this.city = city;
        this.district = district;
        this.xPos = xPos;
        this.yPos = yPos;
        this.record = record;
    }
}
