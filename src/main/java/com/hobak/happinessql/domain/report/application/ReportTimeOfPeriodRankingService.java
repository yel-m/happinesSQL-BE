package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.dto.TimeOfDayHappinessResponseDto;
import com.hobak.happinessql.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportTimeOfPeriodRankingService {

    private final RecordRepository recordRepository;

    public List<TimeOfDayHappinessResponseDto> getAllTimeOfDayRankings(User user) {
        List<Record> records = recordRepository.findAllByUser(user);
        return TimeOfDayHappinessAnalyzer.getTimeOfDayRankings(records);
    }

    public List<TimeOfDayHappinessResponseDto> getYearlyTimeOfDayRankings(User user) {
        int currentYear = LocalDate.now().getYear();
        LocalDateTime startOfYear = LocalDateTime.of(currentYear, 1, 1, 0, 0);
        LocalDateTime endOfYear = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59);
        List<Record> records = recordRepository.findAllByCreatedAtBetweenAndUser(startOfYear, endOfYear, user);
        return TimeOfDayHappinessAnalyzer.getTimeOfDayRankings(records);
    }

    public List<TimeOfDayHappinessResponseDto> getMonthlyTimeOfDayRankings(User user) {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        LocalDateTime startOfMonthDateTime = startOfMonth.atStartOfDay();
        LocalDateTime endOfMonthDateTime = endOfMonth.atTime(23, 59, 59);
        List<Record> records = recordRepository.findAllByCreatedAtBetweenAndUser(startOfMonthDateTime, endOfMonthDateTime, user);
        return TimeOfDayHappinessAnalyzer.getTimeOfDayRankings(records);
    }
}
