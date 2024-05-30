package com.hobak.happinessql.domain.report.application;


import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.converter.ReportConverter;
import com.hobak.happinessql.domain.report.domain.AgeGroup;
import com.hobak.happinessql.domain.report.domain.TimeOfDay;
import com.hobak.happinessql.domain.report.dto.SummaryHappinessResponseDto;
import com.hobak.happinessql.domain.user.domain.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendSummaryService {

    private final RecordRepository recordRepository;

    public SummaryHappinessResponseDto getSummary(AgeGroup ageGroup, Gender gender) {
        List<Record> records = getFilteredRecords(ageGroup, gender);
        return (!records.isEmpty()) ? generateTrendSummary(records) : null;
    }

    public List<Record> getFilteredRecords(AgeGroup ageGroup, Gender gender) {
        if (ageGroup == null && gender == null) {
            return recordRepository.findAll();
        } else if (ageGroup == null) {
            return recordRepository.findByUserGender(gender);
        } else if (gender == null) {
            return recordRepository.findByUserAgeBetween(ageGroup.getMinAge(), ageGroup.getMaxAge());
        } else {
            return recordRepository.findByUserAgeBetweenAndUserGender(ageGroup.getMinAge(), ageGroup.getMaxAge(), gender);
        }
    }

    private SummaryHappinessResponseDto generateTrendSummary(List<Record> records) {
        String location = LocationHappinessAnalyzer.getHappiestLocation(records);
        TimeOfDay timeOfDay = TimeOfDayHappinessAnalyzer.getHappiestTimeOfDay(records);
        String activity = ActivityHappinessAnalyzer.getHappiestActivity(records);

        return ReportConverter.toSummaryHappinessResponseDto(timeOfDay, location, activity);
    }

}
