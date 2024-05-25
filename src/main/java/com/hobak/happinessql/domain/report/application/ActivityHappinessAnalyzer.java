package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;

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

        System.out.println("평균 행복도가 높은 Activity : " + happiestActivity + "행복도 : " + maxHappiness);
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
}

