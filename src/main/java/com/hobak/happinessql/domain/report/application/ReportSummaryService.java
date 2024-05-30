package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.converter.ReportConverter;
import com.hobak.happinessql.domain.report.domain.TimeOfDay;
import com.hobak.happinessql.domain.report.dto.SummaryHappinessResponseDto;
import com.hobak.happinessql.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportSummaryService {

    private final RecordRepository recordRepository;

    public SummaryHappinessResponseDto getAllSummary(User user) {
        List<Record> records = recordRepository.findAllByUser(user);
        return generateReportSummary(records);
    }

    public SummaryHappinessResponseDto getAnnualSummary(User user) {
        int currentYear = LocalDate.now().getYear();
        LocalDateTime startOfYear = LocalDateTime.of(currentYear, 1, 1, 0, 0);
        LocalDateTime endOfYear = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59);

        List<Record> annualRecords = recordRepository.findAllByCreatedAtBetweenAndUser(startOfYear, endOfYear, user);
        return generateReportSummary(annualRecords);
    }

    public SummaryHappinessResponseDto getMonthlySummary(User user) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = today.withDayOfMonth(today.lengthOfMonth()).atTime(23, 59, 59);

        List<Record> monthlyRecords = recordRepository.findAllByCreatedAtBetweenAndUser(startOfMonth, endOfMonth, user);
        return generateReportSummary(monthlyRecords);
    }

    private SummaryHappinessResponseDto generateReportSummary(List<Record> records) {
        String location = LocationHappinessAnalyzer.getHappiestLocation(records);
        TimeOfDay timeOfDay = TimeOfDayHappinessAnalyzer.getHappiestTimeOfDay(records);
        String activity = ActivityHappinessAnalyzer.getHappiestActivity(records);

        return ReportConverter.toSummaryHappinessResponseDto(timeOfDay, location, activity);
    }
}
