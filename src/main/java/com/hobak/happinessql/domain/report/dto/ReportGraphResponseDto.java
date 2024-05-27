package com.hobak.happinessql.domain.report.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportGraphResponseDto {
    private ArrayList<String> labels;
    private ArrayList<Double> happiness;
    @Builder
    public ReportGraphResponseDto(ArrayList<String> labels, ArrayList<Double> happiness){
        this.labels = labels;
        this.happiness = happiness;
    }
}
