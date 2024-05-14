package com.hobak.happinessql.domain.record.application;

import com.hobak.happinessql.domain.record.converter.RecordConverter;
import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.dto.RecordResponseDto;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.global.util.TimeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RecordCalendarDetailService {

    private final RecordRepository recordRepository;
    private final UserFindService userFindService;

    public List<RecordResponseDto> getRecords(String date, Long userId) {
        LocalDate dt = TimeConverter.stringToLocalDate(date);
        User user = userFindService.findUserById(userId);
        List<Record> records = findRecordsBy(dt, user);
        return RecordConverter.toRecordResponseDtos(records);
    }

    private List<Record> findRecordsBy(LocalDate date, User user) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59);
        return recordRepository.findAllByCreatedAtBetweenAndUser(startOfDay, endOfDay, user);
    }
}
