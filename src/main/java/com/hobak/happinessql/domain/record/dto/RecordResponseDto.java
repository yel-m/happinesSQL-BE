package com.hobak.happinessql.domain.record.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordResponseDto {

    @JsonProperty("record_id")
    private Long recordId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime date;

    private String memo;

    private String imgUrl;

    private int happiness;

    @Builder
    public RecordResponseDto(Long recordId, LocalDateTime date, String memo, String imgUrl, int happiness) {
        this.recordId = recordId;
        this.date = date;
        this.memo = memo;
        this.imgUrl = imgUrl;
        this.happiness = happiness;
    }
}
