package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.report.domain.TimePeriod;

import java.util.*;
import java.util.stream.Collectors;

public class TimePeriodHappinessAnalyzer {

    public static TimePeriod getHappiestTimePeriod(List<Record> records) {

        if (records == null || records.isEmpty()) {
            return null;
        }

        Map<TimePeriod, List<Integer>> timePeriodHappinessMap = new HashMap<>();

        // 시간대별 행복도를 매핑
        for (Record record : records) {
            TimePeriod timePeriod = TimePeriod.of(record.getCreatedAt().getHour());
            Integer happiness = record.getHappiness();

            timePeriodHappinessMap.computeIfAbsent(timePeriod, k -> new ArrayList<>()).add(happiness);
        }

        // 평균 행복도 계산
        Map<TimePeriod, Double> averageHappinessMap = timePeriodHappinessMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0)));

        // 최대 평균 행복도를 가진 시간대 찾기
        double maxAverageHappiness = averageHappinessMap.values().stream()
                .max(Double::compare)
                .orElse(Double.MIN_VALUE);

        // 최대 평균 행복도를 가진 시간대 후보들 필터링
        List<TimePeriod> candidates = averageHappinessMap.entrySet().stream()
                .filter(entry -> entry.getValue() == maxAverageHappiness)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        for(TimePeriod candidate : candidates) {
            System.out.println("candidate : " + candidate);
        }

        // 후보 중 빈도수가 가장 높은 시간대 찾기, 동일 빈도시 랜덤 선택
        return candidates.stream()
                .max(Comparator.comparingInt(tp -> timePeriodHappinessMap.get(tp).size()))
                .orElseGet(() -> candidates.get(new Random().nextInt(candidates.size())));
    }
}
