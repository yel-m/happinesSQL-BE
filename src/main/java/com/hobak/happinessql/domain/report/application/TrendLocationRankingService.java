package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.dto.LocationActivityRankingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendLocationRankingService {

    private final RecordRepository recordRepository;

    public List<LocationActivityRankingResponseDto> getTop3HappyLocationsWithActivities() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        List<Record> records = recordRepository.findAllByCreatedAtBetween(startOfDay, endOfDay);
        return LocationHappinessAnalyzer.getLocationActivityRankings(records, 3);
    }
}
