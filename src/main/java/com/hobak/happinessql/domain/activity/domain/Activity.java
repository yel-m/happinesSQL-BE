package com.hobak.happinessql.domain.activity.domain;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.global.infra.database.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Getter
@Entity
@Table(name = "activity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class Activity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long activityId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String emoji;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    private List<Record> records;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    public Activity(Long activityId, String name, Category category) {
        this.activityId = activityId;
        this.name = name;
        this.category = category;
    }
    public void updateName(String name){
        this.name = name;
    }
}

