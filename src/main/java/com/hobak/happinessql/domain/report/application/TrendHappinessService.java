package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.converter.TrendConverter;
import com.hobak.happinessql.domain.report.domain.HappinessLevel;
import com.hobak.happinessql.domain.report.dto.TrendHappinessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrendHappinessService {
    private final RecordRepository recordRepository;
    public TrendHappinessResponseDto getTrendHappiness() {
        double totalHappiness = recordRepository.findAll().stream().mapToInt(Record::getHappiness).sum();
        double averageHappiness = totalHappiness / recordRepository.count();
        averageHappiness = Math.round(averageHappiness * 100.0) / 100.0;
        HappinessLevel level = HappinessLevel.of(averageHappiness);
        String emoji;
        if (averageHappiness >= 0 && averageHappiness < 2) {
            emoji = "😱";
        } else if (averageHappiness >= 3 && averageHappiness < 4) {
            emoji = "🙁";
        } else if (averageHappiness >= 4 && averageHappiness < 5) {
            emoji = "😐";
        } else if (averageHappiness >= 5 && averageHappiness < 6) {
            emoji = "🙂";
        } else {
            emoji = "😄";
        }
        return TrendConverter.toTrendHappinessResponseDto(averageHappiness, level, emoji);
    }
}
