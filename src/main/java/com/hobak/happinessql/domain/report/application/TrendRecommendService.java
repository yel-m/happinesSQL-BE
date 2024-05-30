package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.dto.TrendRecommendActivityResponseDto;
import com.hobak.happinessql.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendRecommendService {
    private final RecordRepository recordRepository;

    public List<TrendRecommendActivityResponseDto> getRecommendActivities(User user) {
        List<Record> records = recordRepository.findAllExceptUser(user.getUserId());
        return ActivityHappinessAnalyzer.getRandomHappyActivities(records, 3);
    }
}
