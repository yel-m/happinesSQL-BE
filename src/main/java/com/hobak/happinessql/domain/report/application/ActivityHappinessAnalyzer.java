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
        Map<String, List<Record>> activityRecordsMap = records.stream()
                .filter(record -> record.getActivity() != null)
                .collect(Collectors.groupingBy(record -> record.getActivity().getName()));

        // 각 Activity별 평균 행복도와 빈도 계산
        Map<String, Double> activityAverageHappiness = new HashMap<>();
        Map<String, Integer> activityFrequency = new HashMap<>();

        activityRecordsMap.forEach((activity, recordList) -> {
            activityAverageHappiness.put(activity, recordList.stream()
                    .mapToInt(Record::getHappiness)
                    .average()
                    .orElse(Double.NaN));
            activityFrequency.put(activity, recordList.size());
        });

        // 평균 행복도가 가장 높은 Activity 찾기
        double maxHappiness = Collections.max(activityAverageHappiness.values());

        // 평균 행복도가 가장 높은 Activity들 중 빈도가 가장 높은 Activity 찾기
        Optional<String> happiestActivity = activityAverageHappiness.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHappiness)
                .max(Comparator.comparing(entry -> activityFrequency.get(entry.getKey())))
                .map(Map.Entry::getKey);

        // 평균 행복도와 빈도가 같은 Activity가 여러 개인 경우, 랜덤으로 선택
        List<String> happiestActivities = activityAverageHappiness.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHappiness)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (happiestActivities.size() > 1) {
            Collections.shuffle(happiestActivities);
            return happiestActivities.get(0);
        } else {
            return happiestActivity.orElse(null);
        }
    }

    public static List<ActivityHappinessDto> getActivityRankings(List<Record> records, int topCount) {
        if (records == null || records.isEmpty()) {
            return null;
        }

        // 활동 그룹화 및 이모지 매핑
        Map<String, String> activityEmojiMap = getActivityEmojiMap(records);

        // 활동별 평균 행복도 계산
        Map<String, Double> activityAverageHappiness = new HashMap<>();
        records.stream()
                .filter(record -> record.getActivity() != null)
                .collect(Collectors.groupingBy(record -> record.getActivity().getName()))
                .forEach((activity, recordList) -> {
                    double averageHappiness = recordList.stream()
                            .mapToInt(Record::getHappiness)
                            .average()
                            .orElse(Double.NaN);
                    activityAverageHappiness.put(activity, averageHappiness);
                });

        // 평균 행복도가 높은 상위 N개의 활동 선정
        List<ActivityHappinessDto> activityRankings = activityAverageHappiness.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(topCount)
                .map(entry -> {
                    String activity = entry.getKey();
                    double averageHappiness = entry.getValue();
                    String emoji = activityEmojiMap.get(activity); // 이모지 가져오기
                    return ReportConverter.toActivityHappinessDto(0, activity, emoji);
                })
                .collect(Collectors.toList());

        // 랭킹 추가
        for (int i = 0; i < activityRankings.size(); i++) {
            activityRankings.get(i).setRanking(i + 1);
        }

        return activityRankings;
    }

    // 활동별 이모지 매핑 생성
    private static Map<String, String> getActivityEmojiMap(List<Record> records) {
        return records.stream()
                .filter(record -> record.getActivity() != null)
                .collect(Collectors.toMap(
                        record -> record.getActivity().getName(),
                        record -> record.getActivity().getEmoji(),
                        (existing, replacement) -> existing)); // 기존 값 유지
    }
}

