package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.converter.ReportConverter;
import com.hobak.happinessql.domain.report.domain.HappinessLevel;
import com.hobak.happinessql.domain.report.dto.AverageHappinessResponseDto;
import com.hobak.happinessql.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AverageHappinessService {
    private final RecordRepository recordRepository;
    LocalDate currentDate = LocalDate.now();
    int currentYear = currentDate.getYear();
    int currentMonth = currentDate.getMonthValue();
    public AverageHappinessResponseDto getTrendHappiness() {
        double totalHappiness = recordRepository.findAll().stream().mapToInt(Record::getHappiness).sum();
        double averageHappiness = totalHappiness / recordRepository.count();
        averageHappiness = Math.round(averageHappiness * 100.0) / 100.0;
        HappinessLevel level = HappinessLevel.of(averageHappiness);
        return ReportConverter.toAverageHappinessResponseDto(averageHappiness,level,level.getEmoji());
    }
    public AverageHappinessResponseDto getAllHappiness(User user) {
        double totalHappiness = recordRepository.findAllByUser(user).stream().mapToInt(Record::getHappiness).sum();
        double averageHappiness = totalHappiness / recordRepository.countAllByUser(user);
        averageHappiness = Math.round(averageHappiness * 100.0) / 100.0;
        HappinessLevel level = HappinessLevel.of(averageHappiness);
        return ReportConverter.toAverageHappinessResponseDto(averageHappiness,level,level.getEmoji());
    }
    public AverageHappinessResponseDto getAnnualHappiness(User user) {
        LocalDateTime startOfYear = LocalDateTime.of(currentYear, 1, 1, 0, 0);
        LocalDateTime endOfYear = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59);
        double totalHappiness = recordRepository.findAllByCreatedAtBetweenAndUser(startOfYear, endOfYear, user).stream().mapToInt(Record::getHappiness).sum();
        double averageHappiness = totalHappiness / recordRepository.countAllByCreatedAtBetweenAndUser(startOfYear, endOfYear, user);
        averageHappiness = Math.round(averageHappiness * 100.0) / 100.0;
        HappinessLevel level = HappinessLevel.of(averageHappiness);
        return ReportConverter.toAverageHappinessResponseDto(averageHappiness,level,level.getEmoji());    }
    public AverageHappinessResponseDto getMonthlyHappiness(User user) {
        LocalDateTime startOfMonth = LocalDateTime.of(currentYear, currentMonth, 1, 0, 0);
        LocalDateTime endOfMonth = LocalDateTime.of(currentYear, currentMonth, 31, 23, 59, 59);
        double totalHappiness = recordRepository.findAllByCreatedAtBetweenAndUser(startOfMonth, endOfMonth, user).stream().mapToInt(Record::getHappiness).sum();
        double averageHappiness = totalHappiness / recordRepository.countAllByCreatedAtBetweenAndUser(startOfMonth, endOfMonth, user);
        averageHappiness = Math.round(averageHappiness * 100.0) / 100.0;
        HappinessLevel level = HappinessLevel.of(averageHappiness);
        return ReportConverter.toAverageHappinessResponseDto(averageHappiness,level,level.getEmoji());
    }
}
