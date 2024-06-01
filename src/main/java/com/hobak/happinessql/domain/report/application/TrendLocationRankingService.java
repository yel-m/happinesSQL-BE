package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.dto.LocationActivityRankingResponseDto;
import com.hobak.happinessql.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendLocationRankingService {

    private final RecordRepository recordRepository;

    public List<LocationActivityRankingResponseDto> getTop3HappyLocationsWithActivities(User user) {
        List<Record> records = recordRepository.findAllByUser(user);
        return LocationHappinessAnalyzer.getLocationActivityRankings(records, 3);

    }
}
