package com.hobak.happinessql.global.util;

import java.util.List;


public class Calculator {
    public static Integer getAverage(List<Integer> happinessIndexes) {
        if (happinessIndexes == null || happinessIndexes.isEmpty()) {
            return 0;
        }

        Integer sum = 0;
        for(Integer happinessIndex : happinessIndexes) {
            sum += happinessIndex;
        }

        double originalAverage = (double) sum / happinessIndexes.size();

        double newAverage = ((originalAverage - 1) / (7 - 1)) * (5 - 1) + 1;

        return (int) Math.round(newAverage);
    }
}
