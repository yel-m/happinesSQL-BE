package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.converter.ReportConverter;
import com.hobak.happinessql.domain.report.dto.ReportGraphResponseDto;
import com.hobak.happinessql.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ReportGraphService {
    private final RecordRepository recordRepository;
    LocalDate currentDate = LocalDate.now();
    int currentYear = currentDate.getYear();
    int currentMonth = currentDate.getMonthValue();


    public ReportGraphResponseDto getAnnualGraph(User user){
        ArrayList<String> labels = new ArrayList<>(Arrays.asList(
                "1월", "2월", "3월", "4월", "5월", "6월",
                "7월", "8월", "9월", "10월", "11월", "12월"
        ));
        ArrayList<Double> happiness = new ArrayList<>();
        for(int month = 1; month <= 12; month++) {
            LocalDate startDate = LocalDate.of(currentYear, month, 1);
            LocalDate endDate;
            if(month < 12) {
                endDate = LocalDate.of(currentYear, month+1, 1);
            }
            else {
                endDate = LocalDate.of(currentYear+1, 1, 1);
            }
            List<Record> monthlyRecords = recordRepository.findAllByCreatedAtBetweenAndUser(startDate.atStartOfDay(), endDate.atStartOfDay(), user);
            if (!monthlyRecords.isEmpty()) {
                double sum = monthlyRecords.stream().mapToInt(Record::getHappiness).sum();
                double average = sum / monthlyRecords.size();
                happiness.add(average);
            }
            else {
                happiness.add(0.0);
            }
        }
        return ReportConverter.toReportGraphResponseDto(labels,happiness);
    }
    public ReportGraphResponseDto getMonthlyGraph(User user){
        ArrayList<String> labels = new ArrayList<>(Arrays.asList(
                "첫째 주", "둘째 주", "셋째 주", "넷째 주", "다섯째 주"
        ));
        ArrayList<Double> happiness = new ArrayList<>();
        LocalDate startOfMonth = LocalDate.of(currentYear, currentMonth, 1);
        LocalDate startOfWeek = startOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        for(int week = 1; week<=5; week++) {
            LocalDate endOfWeek = startOfWeek.plusDays(7);
            List<Record> weeklyRecords = recordRepository.findAllByCreatedAtBetweenAndUser(startOfWeek.atStartOfDay(), endOfWeek.atStartOfDay(), user);
            if(!weeklyRecords.isEmpty()) {
                double sum = weeklyRecords.stream().mapToInt(Record::getHappiness).sum();
                double average = sum / weeklyRecords.size();
                happiness.add(average);
            }
            else {
                happiness.add(0.0);
            }
            startOfWeek = startOfWeek.plusDays(7);
        }
        return ReportConverter.toReportGraphResponseDto(labels, happiness);

    }
}
