package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;

import java.util.*;
import java.util.stream.Collectors;

public class LocationHappinessAnalyzer {

    public static String getHappiestLocation(List<Record> records) {

        if (records == null || records.isEmpty()) {
            return null;
        }

        // 도시와 구를 기준으로 Record 그룹화
        Map<String, List<Record>> locationRecordsMap = records.stream()
                .filter(record -> record.getLocation() != null)
                .collect(Collectors.groupingBy(record ->
                        record.getLocation().getCity() + " " + record.getLocation().getDistrict()));

        // 각 위치별 평균 행복도와 빈도 계산
        Map<String, Double> locationAverageHappiness = new HashMap<>();
        Map<String, Integer> locationFrequency = new HashMap<>();

        locationRecordsMap.forEach((location, recordList) -> {
            locationAverageHappiness.put(location, recordList.stream()
                    .mapToInt(Record::getHappiness)
                    .average()
                    .orElse(Double.NaN));
            locationFrequency.put(location, recordList.size());
        });

        // 평균 행복도가 가장 높은 위치 찾기
        double maxHappiness = Collections.max(locationAverageHappiness.values());

        // 평균 행복도가 가장 높은 위치들 중 빈도가 가장 높은 위치를  찾기
        Optional<String> happiestLocation = locationAverageHappiness.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHappiness)
                .max(Comparator.comparing(entry -> locationFrequency.get(entry.getKey())))
                .map(Map.Entry::getKey);

        // 평균 행복도와 빈도가 같다면, 랜덤으로 선택
        List<String> happiestLocations = locationAverageHappiness.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHappiness)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (happiestLocations.size() > 1) {
            Collections.shuffle(happiestLocations);
            return happiestLocations.get(0);
        } else {
            return happiestLocation.orElse(null);
        }
    }

}
