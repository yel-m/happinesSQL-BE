package com.hobak.happinessql.domain.record.domain;

import com.hobak.happinessql.global.infra.database.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "analysis")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Analysis extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_id")
    private Long analysisId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @ElementCollection
    @CollectionTable(name = "positive_sentiments", joinColumns = @JoinColumn(name = "analysis_id"))
    @Column(name = "content")
    private List<String> positiveSentiments = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "negative_sentiments", joinColumns = @JoinColumn(name = "analysis_id"))
    @Column(name = "content")
    private List<String> negativeSentiments = new ArrayList<>();

    @Builder
    public Analysis(Record record, List<String> positiveSentiments, List<String> negativeSentiments) {
        this.record = record;
        this.positiveSentiments = positiveSentiments;
        this.negativeSentiments = negativeSentiments;
    }



}
