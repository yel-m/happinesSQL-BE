package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Location;
import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.report.converter.ReportConverter;
import com.hobak.happinessql.domain.report.dto.LocationActivityRankingResponseDto;
import com.hobak.happinessql.domain.report.dto.LocationRankingResponseDto;

import java.util.*;
import java.util.stream.Collectors;

public class LocationHappinessAnalyzer {

    public static String getHappiestLocation(List<Record> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }

        // 도시와 구를 기준으로 Record 그룹화
        // TODO : 나중에 주석 삭제 및 랜덤 돌리는 로직 메서드로 추출해서 Ranking 관련 메서드에도 추가
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

    public static List<LocationRankingResponseDto> getLocationRankings(List<Record> records, int topCount) {
        List<LocationRankingResponseDto> locationRankings = new ArrayList<>();
        if (records == null || records.isEmpty()) {
            // 데이터가 없는 경우에도 빈 LocationHappinessDto 객체를 topCount만큼 추가
            for (int i = 0; i < topCount; i++) {
                locationRankings.add(ReportConverter.toLocationRankingResponseDto(i + 1, null));
            }
            return locationRankings;
        }

        locationRankings = getLocationRankings(records);
        // 만약 topCount보다 적게 선정된 경우, 나머지 빈 항목 추가
        while (locationRankings.size() < topCount) {
            locationRankings.add(ReportConverter.toLocationRankingResponseDto(locationRankings.size() + 1, null));
        }

        return locationRankings.stream()
                .limit(topCount)
                .collect(Collectors.toList());
    }

    public static List<LocationRankingResponseDto> getLocationRankings(List<Record> records) {
        List<LocationRankingResponseDto> locationRankings = new ArrayList<>();

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
            LocationRankingResponseDto locationDto = ReportConverter.toLocationRankingResponseDto(i + 1, location);
            locationRankings.add(locationDto);
        }

        return locationRankings;
    }

    public static List<LocationActivityRankingResponseDto> getLocationActivityRankings(List<Record> records, int topCount) {
        List<LocationActivityRankingResponseDto> locationActivityRankings = new ArrayList<>();
        if(records == null || records.isEmpty()) {
            for(int i = 0; i < topCount; i++) {
                locationActivityRankings.add(ReportConverter.toLocationActivityRankingResponseDto(i + 1, null, null));
            }
            return locationActivityRankings;
        }

        Map<String, List<Record>> locationRecordsMap = groupRecordsByLocation(records);

        Map<String, Double> locationAverageHappiness = calculateLocationAverageHappiness(locationRecordsMap);
        Map<String, Integer> locationFrequency = calculateLocationFrequency(locationRecordsMap);

        List<String> sortedLocations = sortLocations(locationAverageHappiness, locationFrequency);

        for(int i = 0; i < sortedLocations.size(); i++) {
            String locationStr = sortedLocations.get(i);
            List<Record> locationRecords = locationRecordsMap.get(locationStr);

            Record happiestRecord = locationRecords.stream()
                    .max(Comparator.comparingInt(Record::getHappiness))
                    .orElse(null);

            Location location = happiestRecord != null ? happiestRecord.getLocation() : null;
            String happiesActivity = happiestRecord != null ? happiestRecord.getActivity().getName() : null;

            LocationActivityRankingResponseDto dto = ReportConverter.toLocationActivityRankingResponseDto(
                    i+1,
                    location,
                    happiesActivity
            );
            locationActivityRankings.add(dto);
        }

        while(locationActivityRankings.size() < topCount) {
            locationActivityRankings.add(ReportConverter.toLocationActivityRankingResponseDto(locationActivityRankings.size() + 1, null, null));
        }

        return locationActivityRankings.stream()
                .limit(topCount)
                .collect(Collectors.toList());

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
