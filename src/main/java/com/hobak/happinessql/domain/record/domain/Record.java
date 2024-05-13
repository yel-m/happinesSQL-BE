package com.hobak.happinessql.domain.record.domain;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.global.infra.database.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;

    @Column(nullable = false)
    private Integer happiness;

    @Column(nullable = true)
    private String memo;

    @OneToOne(mappedBy = "record", fetch = FetchType.LAZY)
    private RecordImg recordImg;

    @OneToOne(mappedBy = "record", fetch = FetchType.LAZY)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Record(Integer happiness, String memo, User user, Activity activity) {
        this.happiness = happiness;
        this.memo = memo;
        this.user = user;
        this.activity = activity;
    }
}
