package com.hobak.happinessql.domain.user.domain;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.user.dto.UserProfileUpdateRequestDto;
import com.hobak.happinessql.global.infra.database.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Record> records;


    @Builder
    public User(String username, String name, String password, Gender gender, int age) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.age = age;
    }


    public void updateUserProfile(UserProfileUpdateRequestDto requestDto) {
        this.gender = requestDto.getGender();
        this.age = requestDto.getAge();
    }
}