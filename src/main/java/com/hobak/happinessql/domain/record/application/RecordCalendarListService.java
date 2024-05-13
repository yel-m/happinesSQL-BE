package com.hobak.happinessql.domain.record.application;

import java.time.LocalDate;

import com.hobak.happinessql.domain.record.converter.RecordConverter;
import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.dto.RecordCalendarResponseDto;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.domain.User;

import com.hobak.happinessql.global.util.Calculator;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordCalendarListService {
    private final RecordRepository recordRepository;
    private final UserFindService userFindService;

    public List<RecordCalendarResponseDto> getHappinessAverages(Integer month, Integer year, Long userId) {
        User user = userFindService.findUserById(userId);

        if(year == null) {
            year = LocalDate.now().getYear();
        }

        if(month == null) {
            month = LocalDate.now().getMonthValue();
        }

        LocalDateTime startOfMonth = YearMonth.of(year, month).atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59);

        List<Record> records = recordRepository.findAllByCreatedAtBetweenAndUser(startOfMonth, endOfMonth, user);

        Map<LocalDate, List<Integer>> happinessIndexesByDate = records.stream()
                .collect(Collectors.groupingBy(
                        record -> record.getCreatedAt().toLocalDate(),
                        Collectors.mapping(Record::getHappiness, Collectors.toList())
                ));

        List<RecordCalendarResponseDto> responseDtos = new ArrayList<>();
        happinessIndexesByDate.forEach((date, happinessIndexes) -> {
            Integer averageHappiness = Calculator.getAverage(happinessIndexes);
            RecordCalendarResponseDto dto = RecordConverter.toRecordCalendarResponseDto(date, averageHappiness);
            responseDtos.add(dto);
        });

        return responseDtos.stream()
                .sorted(Comparator.comparing(RecordCalendarResponseDto::getDate))
                .collect(Collectors.toList());
    }
}
