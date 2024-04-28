package com.hobak.happinessql.domain.record.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordCreateResponseDto {

    @JsonProperty("record_id")
    private Long recordId;

    @Builder
    public RecordCreateResponseDto(Long recordId) {
        this.recordId = recordId;
    }
}
