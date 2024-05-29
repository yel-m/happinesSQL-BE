package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.converter.TrendConverter;
import com.hobak.happinessql.domain.report.dto.TrendPopularActivitiesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendPopularActivitiesService {
    private final RecordRepository recordRepository;
    public List<TrendPopularActivitiesResponseDto> getPopularActivities(){
        LocalDate today = LocalDate.now();
        List<Object[]> results = recordRepository.findPopularActivities(today.atStartOfDay());
        List<Activity> popularActivities = results.stream()
                .map(result -> (Activity) result[0])
                .toList();
        List<Long> times = results.stream()
                .map(result -> ((Number) result[1]).longValue())
                .toList();
        int ranking = 1;
        List<TrendPopularActivitiesResponseDto> responseDtos = new ArrayList<>();
        for(int i = 0; i<popularActivities.size(); i++) {
            Activity activity = popularActivities.get(i);
            responseDtos.add(TrendConverter
                    .toTrendPopularActivitiesResponseDto(ranking++, activity.getName(),activity.getEmoji(), times.get(i)));
        }
        return responseDtos;
    }
}
