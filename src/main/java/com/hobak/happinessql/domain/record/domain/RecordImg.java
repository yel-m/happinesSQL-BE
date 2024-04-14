package com.hobak.happinessql.domain.record.domain;

import com.hobak.happinessql.global.infra.database.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "record_img")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordImg extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_img_id")
    private Long recordImgId;

    @Column(nullable = true)
    private String url;

    @OneToOne
    @JoinColumn(name = "record_id")
    private Record record;

    @Builder
    public RecordImg(String url, Record record) {
        this.url = url;
        this.record = record;
    }
}