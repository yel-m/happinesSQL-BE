package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.report.converter.ReportConverter;
import com.hobak.happinessql.domain.report.dto.ActivityHappinessDto;

import java.util.*;
import java.util.stream.Collectors;

public class ActivityHappinessAnalyzer {

    public static String getHappiestActivity(List<Record> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }

        // Activity를 기준으로 Record 그룹화
        Map<String, List<Record>> activityRecordsMap = groupRecordsByActivity(records);

        // 활동별 평균 행복도와 빈도 계산
        Map<String, Double> activityAverageHappiness = calculateActivityAverageHappiness(activityRecordsMap);
        Map<String, Integer> activityFrequency = calculateActivityFrequency(activityRecordsMap);

        // 평균 행복도가 가장 높은 활동 찾기
        double maxHappiness = Collections.max(activityAverageHappiness.values());

        // 평균 행복도가 가장 높은 활동들 중 빈도가 가장 높은 활동 찾기
        Optional<String> happiestActivity = findHappiestActivity(activityAverageHappiness, activityFrequency, maxHappiness);

        // 평균 행복도와 빈도가 같은 활동이 여러 개인 경우, 랜덤으로 선택
        List<String> happiestActivities = findActivitiesWithMaxHappiness(activityAverageHappiness, maxHappiness);

        if (happiestActivities.size() > 1) {
            Collections.shuffle(happiestActivities);
            return happiestActivities.get(0);
        } else {
            return happiestActivity.orElse(null);
        }
    }

    public static List<ActivityHappinessDto> getActivityRankings(List<Record> records, int topCount) {
        List<ActivityHappinessDto> activityRankings = new ArrayList<>();

        if (records == null || records.isEmpty()) {
            // 데이터가 없는 경우에도 빈 ActivityHappinessDto 객체를 topCount만큼 추가
            for (int i = 0; i < topCount; i++) {
                activityRankings.add(ReportConverter.toActivityHappinessDto(i + 1, null, null));
            }
            return activityRankings;
        }

        activityRankings = getActivityRankings(records);

        // 만약 topCount보다 적게 선정된 경우, 나머지 빈 항목 추가
        while (activityRankings.size() < topCount) {
            activityRankings.add(ReportConverter.toActivityHappinessDto(activityRankings.size() + 1, null, null));
        }

        return activityRankings.stream()
                .limit(topCount)
                .collect(Collectors.toList());
    }

    public static List<ActivityHappinessDto> getActivityRankings(List<Record> records) {
        List<ActivityHappinessDto> activityRankings = new ArrayList<>();

        // 활동 그룹화 및 이모지 매핑
        Map<String, String> activityEmojiMap = getActivityEmojiMap(records);

        // 활동별 평균 행복도와 빈도 계산
        Map<String, List<Record>> activityRecordsMap = groupRecordsByActivity(records);
        Map<String, Double> activityAverageHappiness = calculateActivityAverageHappiness(activityRecordsMap);
        Map<String, Integer> activityFrequency = calculateActivityFrequency(activityRecordsMap);

        // 평균 행복도와 빈도를 기준으로 활동들을 정렬
        List<String> sortedActivities = sortActivities(activityAverageHappiness, activityFrequency);

        // 상위 N개의 활동 선정
        for (int i = 0; i < sortedActivities.size(); i++) {
            String activity = sortedActivities.get(i);
            String emoji = activityEmojiMap.get(activity); // 이모지 가져오기
            ActivityHappinessDto activityDto = ReportConverter.toActivityHappinessDto(i + 1, activity, emoji);
            activityRankings.add(activityDto);
        }

        return activityRankings;
    }

    private static Map<String, List<Record>> groupRecordsByActivity(List<Record> records) {
        return records.stream()
                .filter(record -> record.getActivity() != null)
                .collect(Collectors.groupingBy(record -> record.getActivity().getName()));
    }

    private static Map<String, Double> calculateActivityAverageHappiness(Map<String, List<Record>> activityRecordsMap) {
        Map<String, Double> activityAverageHappiness = new HashMap<>();
        activityRecordsMap.forEach((activity, recordList) -> {
            double averageHappiness = recordList.stream()
                    .mapToInt(Record::getHappiness)
                    .average()
                    .orElse(Double.NaN);
            activityAverageHappiness.put(activity, averageHappiness);
        });
        return activityAverageHappiness;
    }

    private static Map<String, Integer> calculateActivityFrequency(Map<String, List<Record>> activityRecordsMap) {
        Map<String, Integer> activityFrequency = new HashMap<>();
        activityRecordsMap.forEach((activity, recordList) -> {
            activityFrequency.put(activity, recordList.size());
        });
        return activityFrequency;
    }

    private static Optional<String> findHappiestActivity(Map<String, Double> activityAverageHappiness,
                                                         Map<String, Integer> activityFrequency,
                                                         double maxHappiness) {
        return activityAverageHappiness.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHappiness)
                .max(Comparator.comparing(entry -> activityFrequency.get(entry.getKey())))
                .map(Map.Entry::getKey);
    }

    private static List<String> findActivitiesWithMaxHappiness(Map<String, Double> activityAverageHappiness,
                                                               double maxHappiness) {
        return activityAverageHappiness.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHappiness)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static List<String> sortActivities(Map<String, Double> activityAverageHappiness,
                                               Map<String, Integer> activityFrequency) {
        return activityAverageHappiness.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    int compare = Double.compare(entry2.getValue(), entry1.getValue());
                    if (compare == 0) {
                        compare = Integer.compare(activityFrequency.get(entry2.getKey()), activityFrequency.get(entry1.getKey()));
                    }
                    if (compare == 0) {
                        compare = entry1.getKey().compareTo(entry2.getKey());
                    }
                    return compare;
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static Map<String, String> getActivityEmojiMap(List<Record> records) {
        return records.stream()
                .filter(record -> record.getActivity() != null)
                .collect(Collectors.toMap(
                        record -> record.getActivity().getName(),
                        record -> record.getActivity().getEmoji(),
                        (existing, replacement) -> existing)); // 기존 값 유지
    }
}

