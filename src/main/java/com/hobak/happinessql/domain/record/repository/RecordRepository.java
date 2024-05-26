package com.hobak.happinessql.domain.record.repository;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Page<Record> findByUserOrderByRecordIdDesc(User user, Pageable pageRequest);
    Page<Record> findByRecordIdLessThanAndUserOrderByRecordIdDesc(Long recordId, User user, Pageable pageRequest);
    List<Record> findAllByCreatedAtBetweenAndUser(LocalDateTime startOfMonth, LocalDateTime endOfMonth, User user);
    List<Record> findAllByUser(User user);
}
