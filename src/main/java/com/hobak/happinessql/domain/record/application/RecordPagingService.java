package com.hobak.happinessql.domain.record.application;

import com.hobak.happinessql.domain.record.converter.RecordConverter;
import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.dto.RecordResponseDto;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RecordPagingService {

    private final RecordRepository recordRepository;
    private final UserFindService userFindService;

    public List<RecordResponseDto> fetchRecordPagesBy(Long lastRecordId, int size, Long userId) {
        User user = userFindService.findUserById(userId);

        Page<Record> records;
        if(lastRecordId == null) {
            records = fetchPages(size, user);
        } else {
            records = fetchPages(lastRecordId, size, user);
        }

        return RecordConverter.toRecordResponseDtos(records.getContent());
    }

    private Page<Record> fetchPages(int size, User user) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return recordRepository.findByUserOrderByRecordIdDesc(user, pageRequest);
    }

    private Page<Record> fetchPages(Long lastRecordId, int size, User user) {
        PageRequest pageRequest = PageRequest.of(0, size); // 페이지네이션을 위한 PageRequest, 페이지는 0으로 고정한다.
        return recordRepository.findByRecordIdLessThanAndUserOrderByRecordIdDesc(lastRecordId, user, pageRequest); // JPA 쿼리 메서드
    }
}
