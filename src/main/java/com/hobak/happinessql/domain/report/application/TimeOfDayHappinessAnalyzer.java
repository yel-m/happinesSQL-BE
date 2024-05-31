package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.report.converter.ReportConverter;
import com.hobak.happinessql.domain.report.domain.TimeOfDay;
import com.hobak.happinessql.domain.report.dto.TimeOfDayRankingResponseDto;

import java.util.*;
import java.util.stream.Collectors;

public class TimeOfDayHappinessAnalyzer {

    public static TimeOfDay getHappiestTimeOfDay(List<Record> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }

        // 시간대를 기준으로 Record 그룹화
        Map<TimeOfDay, List<Record>> timeOfDayRecordsMap = groupRecordsByTimeOfDay(records);

        // 시간대별 평균 행복도와 빈도 계산
        Map<TimeOfDay, Double> timeOfDayAverageHappiness = calculateTimeOfDayAverageHappiness(timeOfDayRecordsMap);
        Map<TimeOfDay, Integer> timeOfDayFrequency = calculateTimeOfDayFrequency(timeOfDayRecordsMap);

        // 평균 행복도가 가장 높은 시간대 찾기
        double maxHappiness = Collections.max(timeOfDayAverageHappiness.values());

        // 평균 행복도가 가장 높은 시간대들 중 빈도가 가장 높은 시간대 찾기
        Optional<TimeOfDay> happiestTimeOfDay = findHappiestTimeOfDay(timeOfDayAverageHappiness, timeOfDayFrequency, maxHappiness);

        // 평균 행복도와 빈도가 같은 시간대가 여러 개인 경우, 랜덤으로 선택
        List<TimeOfDay> happiestTimesOfDay = findTimesOfDayWithMaxHappiness(timeOfDayAverageHappiness, maxHappiness);

        if (happiestTimesOfDay.size() > 1) {
            Collections.shuffle(happiestTimesOfDay);
            return happiestTimesOfDay.get(0);
        } else {
            return happiestTimeOfDay.orElse(null);
        }
    }

    public static List<TimeOfDayRankingResponseDto> getTimeOfDayRankings(List<Record> records) {
        List<TimeOfDayRankingResponseDto> timeOfDayRankings = new ArrayList<>();

        // 시간대별 평균 행복도와 빈도 계산
        Map<TimeOfDay, List<Record>> timeOfDayRecordsMap = groupRecordsByTimeOfDay(records);
        Map<TimeOfDay, Double> timeOfDayAverageHappiness = calculateTimeOfDayAverageHappiness(timeOfDayRecordsMap);
        Map<TimeOfDay, Integer> timeOfDayFrequency = calculateTimeOfDayFrequency(timeOfDayRecordsMap);

        // 평균 행복도와 빈도를 기준으로 시간대들을 정렬
        List<TimeOfDay> sortedTimesOfDay = sortTimesOfDay(timeOfDayAverageHappiness, timeOfDayFrequency);

        // 상위 N개의 시간대 선정
        for (int i = 0; i < sortedTimesOfDay.size(); i++) {
            TimeOfDay timeOfDay = sortedTimesOfDay.get(i);
            TimeOfDayRankingResponseDto timeOfDayDto = ReportConverter.toTimeOfDayRankingResponseDto(i + 1, timeOfDay);
            timeOfDayRankings.add(timeOfDayDto);
        }

        return timeOfDayRankings;
    }

    private static Map<TimeOfDay, List<Record>> groupRecordsByTimeOfDay(List<Record> records) {
        return records.stream()
                .collect(Collectors.groupingBy(record -> TimeOfDay.of(record.getCreatedAt().getHour())));
    }

    private static Map<TimeOfDay, Double> calculateTimeOfDayAverageHappiness(Map<TimeOfDay, List<Record>> timeOfDayRecordsMap) {
        Map<TimeOfDay, Double> timeOfDayAverageHappiness = new HashMap<>();
        timeOfDayRecordsMap.forEach((timeOfDay, recordList) -> {
            double averageHappiness = recordList.stream()
                    .mapToInt(Record::getHappiness)
                    .average()
                    .orElse(Double.NaN);
            timeOfDayAverageHappiness.put(timeOfDay, averageHappiness);
        });
        return timeOfDayAverageHappiness;
    }

    private static Map<TimeOfDay, Integer> calculateTimeOfDayFrequency(Map<TimeOfDay, List<Record>> timeOfDayRecordsMap) {
        Map<TimeOfDay, Integer> timeOfDayFrequency = new HashMap<>();
        timeOfDayRecordsMap.forEach((timeOfDay, recordList) -> {
            timeOfDayFrequency.put(timeOfDay, recordList.size());
        });
        return timeOfDayFrequency;
    }

    private static Optional<TimeOfDay> findHappiestTimeOfDay(Map<TimeOfDay, Double> timeOfDayAverageHappiness,
                                                             Map<TimeOfDay, Integer> timeOfDayFrequency,
                                                             double maxHappiness) {
        return timeOfDayAverageHappiness.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHappiness)
                .max(Comparator.comparing(entry -> timeOfDayFrequency.get(entry.getKey())))
                .map(Map.Entry::getKey);
    }

    private static List<TimeOfDay> findTimesOfDayWithMaxHappiness(Map<TimeOfDay, Double> timeOfDayAverageHappiness,
                                                                  double maxHappiness) {
        return timeOfDayAverageHappiness.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHappiness)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static List<TimeOfDay> sortTimesOfDay(Map<TimeOfDay, Double> timeOfDayAverageHappiness,
                                                  Map<TimeOfDay, Integer> timeOfDayFrequency) {
        return timeOfDayAverageHappiness.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    int compare = Double.compare(entry2.getValue(), entry1.getValue());
                    if (compare == 0) {
                        compare = Integer.compare(timeOfDayFrequency.get(entry2.getKey()), timeOfDayFrequency.get(entry1.getKey()));
                    }
                    if (compare == 0) {
                        compare = entry1.getKey().name().compareTo(entry2.getKey().name());
                    }
                    return compare;
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
