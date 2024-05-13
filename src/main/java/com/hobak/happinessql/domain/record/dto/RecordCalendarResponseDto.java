package com.hobak.happinessql.domain.record.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordCalendarResponseDto {

    LocalDate date;

    Integer happiness;

    @Builder
    public RecordCalendarResponseDto(LocalDate date, Integer happiness) {
        this.date = date;
        this.happiness = happiness;
    }
}
