package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.converter.ReportConverter;
import com.hobak.happinessql.domain.report.domain.TimePeriod;
import com.hobak.happinessql.domain.report.dto.ReportSummaryResponseDto;
import com.hobak.happinessql.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportSummaryService {

    private final RecordRepository recordRepository;

    public ReportSummaryResponseDto getAllSummary(User user) {
        List<Record> records = recordRepository.findAllByUser(user);
        return generateReportSummary(records);
    }

    public ReportSummaryResponseDto getAnnualSummary(User user) {
        Integer currentYear = LocalDate.now().getYear();
        LocalDateTime startOfYear = LocalDateTime.of(currentYear, 1, 1, 0, 0);
        LocalDateTime endOfYear = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59);

        List<Record> annualRecords = recordRepository.findAllByCreatedAtBetweenAndUser(startOfYear, endOfYear, user);
        return generateReportSummary(annualRecords);
    }

    public ReportSummaryResponseDto getWeeklySummary(User user) {
        LocalDate today = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDateTime startOfWeekDateTime = startOfWeek.atStartOfDay();
        LocalDateTime endOfWeekDateTime = startOfWeek.plusDays(6).atTime(23, 59, 59);

        List<Record> weeklyRecords = recordRepository.findAllByCreatedAtBetweenAndUser(startOfWeekDateTime, endOfWeekDateTime, user);
        return generateReportSummary(weeklyRecords);
    }

    private ReportSummaryResponseDto generateReportSummary(List<Record> records) {
        String location = LocationHappinessAnalyzer.getHappiestLocation(records);
        TimePeriod timePeriod = TimePeriodHappinessAnalyzer.getHappiestTimePeriod(records);
        String activity = ActivityHappinessAnalyzer.getHappiestActivity(records);

        return ReportConverter.toReportSummaryResponseDto(timePeriod, location, activity);
    }
}
