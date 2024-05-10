package com.hobak.happinessql.domain.record.converter;

import com.hobak.happinessql.domain.activity.domain.Activity;
import com.hobak.happinessql.domain.record.domain.Location;
import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.dto.RecordCreateRequestDto;
import com.hobak.happinessql.domain.record.dto.RecordCreateResponseDto;
import com.hobak.happinessql.domain.record.dto.RecordResponseDto;
import com.hobak.happinessql.domain.user.domain.User;

import java.util.ArrayList;
import java.util.List;

public class RecordConverter {

    public static Record toRecord(RecordCreateRequestDto requestDto, User user, Activity activity) {
        return Record.builder()
                .happiness(requestDto.getHappiness())
                .memo(requestDto.getMemo())
                .user(user)
                .activity(activity)
                .build();
    }

    public static Location toLocation(RecordCreateRequestDto requestDto, Record record) {
        return Location.builder()
                .fullName(requestDto.getFullName())
                .city(requestDto.getCity())
                .country(requestDto.getCountry())
                .district(requestDto.getDistrict())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .record(record)
                .build();
    }


    public static List<RecordResponseDto> toRecordResponseDtos(List<Record> records) {
        List<RecordResponseDto> recordResponseDtos = new ArrayList<>();
        for(Record record: records) {
            String imgUrl = record.getRecordImg() != null ? record.getRecordImg().getUrl() : null;
            RecordResponseDto recordResponseDto = RecordResponseDto.builder()
                    .recordId(record.getRecordId())
                    .date(record.getCreatedAt())
                    .memo(record.getMemo())
                    .happiness(record.getHappiness())
                    .imgUrl(imgUrl)
                    .build();

            recordResponseDtos.add(recordResponseDto);
        }
        return recordResponseDtos;
    }

    public static RecordCreateResponseDto toRecordCreateResponseDto(Long recordId) {
        return RecordCreateResponseDto
                .builder()
                .recordId(recordId)
                .build();
    }
}
