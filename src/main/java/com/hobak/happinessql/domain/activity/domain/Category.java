package com.hobak.happinessql.domain.activity.domain;
import com.hobak.happinessql.global.infra.database.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Activity> activities;

    @Column(nullable = true)
    private Long userId;

    @Builder
    public Category(Long categoryId, String name, List<Activity> activities, Long userId) {
        this.categoryId = categoryId;
        this.name = name;
        this.activities = activities;
        this.userId = userId;
    }
}

