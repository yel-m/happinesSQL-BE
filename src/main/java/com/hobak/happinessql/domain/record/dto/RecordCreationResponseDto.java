package com.hobak.happinessql.domain.record.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordCreationResponseDto {

    @JsonProperty("record_id")
    private Long recordId;

    @Builder
    public RecordCreationResponseDto(Long recordId) {
        this.recordId = recordId;
    }
}
