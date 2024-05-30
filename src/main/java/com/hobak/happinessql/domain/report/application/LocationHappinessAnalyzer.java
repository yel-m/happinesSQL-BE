package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.report.converter.ReportConverter;
import com.hobak.happinessql.domain.report.dto.LocationHappinessResponseDto;

import java.util.*;
import java.util.stream.Collectors;

public class LocationHappinessAnalyzer {

    public static String getHappiestLocation(List<Record> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }

        // 도시와 구를 기준으로 Record 그룹화
        Map<String, List<Record>> locationRecordsMap = groupRecordsByLocation(records);

        // 위치별 평균 행복도와 빈도 계산
        Map<String, Double> locationAverageHappiness = calculateLocationAverageHappiness(locationRecordsMap);
        Map<String, Integer> locationFrequency = calculateLocationFrequency(locationRecordsMap);

        // 평균 행복도가 가장 높은 위치 찾기
        double maxHappiness = Collections.max(locationAverageHappiness.values());

        // 평균 행복도가 가장 높은 위치들 중 빈도가 가장 높은 위치 찾기
        Optional<String> happiestLocation = findHappiestLocation(locationAverageHappiness, locationFrequency, maxHappiness);

        // 평균 행복도와 빈도가 같다면, 랜덤으로 선택
        List<String> happiestLocations = findLocationsWithMaxHappiness(locationAverageHappiness, maxHappiness);

        if (happiestLocations.size() > 1) {
            Collections.shuffle(happiestLocations);
            return happiestLocations.get(0);
        } else {
            return happiestLocation.orElse(null);
        }
    }

    public static List<LocationHappinessResponseDto> getLocationRankings(List<Record> records, int topCount) {
        List<LocationHappinessResponseDto> locationRankings = new ArrayList<>();
        if (records == null || records.isEmpty()) {
            // 데이터가 없는 경우에도 빈 LocationHappinessDto 객체를 topCount만큼 추가
            for (int i = 0; i < topCount; i++) {
                locationRankings.add(ReportConverter.toLocationHappinessResponseDto(i + 1, null));
            }
            return locationRankings;
        }

        locationRankings = getLocationRankings(records);
        // 만약 topCount보다 적게 선정된 경우, 나머지 빈 항목 추가
        while (locationRankings.size() < topCount) {
            locationRankings.add(ReportConverter.toLocationHappinessResponseDto(locationRankings.size() + 1, null));
        }

        return locationRankings.stream()
                .limit(topCount)
                .collect(Collectors.toList());
    }

    public static List<LocationHappinessResponseDto> getLocationRankings(List<Record> records) {
        List<LocationHappinessResponseDto> locationRankings = new ArrayList<>();

        // 도시와 구를 기준으로 Record 그룹화
        Map<String, List<Record>> locationRecordsMap = groupRecordsByLocation(records);

        // 위치별 평균 행복도와 빈도 계산
        Map<String, Double> locationAverageHappiness = calculateLocationAverageHappiness(locationRecordsMap);
        Map<String, Integer> locationFrequency = calculateLocationFrequency(locationRecordsMap);

        // 평균 행복도와 빈도를 기준으로 위치들을 정렬, 동일한 행복도와 빈도일 경우 사전순 정렬
        List<String> sortedLocations = sortLocations(locationAverageHappiness, locationFrequency);

        // 상위 N개의 위치 선정
        for (int i = 0; i < sortedLocations.size(); i++) {
            String location = sortedLocations.get(i);
            LocationHappinessResponseDto locationDto = ReportConverter.toLocationHappinessResponseDto(i + 1, location);
            locationRankings.add(locationDto);
        }

        return locationRankings;
    }

    private static Map<String, List<Record>> groupRecordsByLocation(List<Record> records) {
        return records.stream()
                .filter(record -> record.getLocation() != null)
                .collect(Collectors.groupingBy(record ->
                        record.getLocation().getCity() + " " + record.getLocation().getDistrict()));
    }

    private static Map<String, Double> calculateLocationAverageHappiness(Map<String, List<Record>> locationRecordsMap) {
        Map<String, Double> locationAverageHappiness = new HashMap<>();
        locationRecordsMap.forEach((location, recordList) -> {
            double averageHappiness = recordList.stream()
                    .mapToInt(Record::getHappiness)
                    .average()
                    .orElse(Double.NaN);
            locationAverageHappiness.put(location, averageHappiness);
        });
        return locationAverageHappiness;
    }

    private static Map<String, Integer> calculateLocationFrequency(Map<String, List<Record>> locationRecordsMap) {
        Map<String, Integer> locationFrequency = new HashMap<>();
        locationRecordsMap.forEach((location, recordList) -> {
            locationFrequency.put(location, recordList.size());
        });
        return locationFrequency;
    }

    private static Optional<String> findHappiestLocation(Map<String, Double> locationAverageHappiness,
                                                         Map<String, Integer> locationFrequency,
                                                         double maxHappiness) {
        return locationAverageHappiness.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHappiness)
                .max(Comparator.comparing(entry -> locationFrequency.get(entry.getKey())))
                .map(Map.Entry::getKey);
    }

    private static List<String> findLocationsWithMaxHappiness(Map<String, Double> locationAverageHappiness,
                                                              double maxHappiness) {
        return locationAverageHappiness.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHappiness)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static List<String> sortLocations(Map<String, Double> locationAverageHappiness,
                                              Map<String, Integer> locationFrequency) {
        return locationAverageHappiness.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    double happinessDiff = entry2.getValue() - entry1.getValue();
                    if (happinessDiff != 0) {
                        return Double.compare(entry2.getValue(), entry1.getValue());
                    } else {
                        int frequencyDiff = locationFrequency.get(entry2.getKey()) - locationFrequency.get(entry1.getKey());
                        if (frequencyDiff != 0) {
                            return Integer.compare(locationFrequency.get(entry2.getKey()), locationFrequency.get(entry1.getKey()));
                        } else {
                            return entry1.getKey().compareTo(entry2.getKey());
                        }
                    }
                })
                .map(Map.Entry::getKey)
                .toList();
    }

}
