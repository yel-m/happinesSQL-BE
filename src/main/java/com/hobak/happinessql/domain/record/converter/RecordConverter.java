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

    public static Record toRecord(RecordCreateRequestDto recordRequestDto, User user, Activity activity) {
        return Record.builder()
                .happiness(recordRequestDto.getHappiness())
                .memo(recordRequestDto.getMemo())
                .user(user)
                .activity(activity)
                .build();
    }

    public static Location toLocation(RecordCreateRequestDto recordRequestDto, Record record) {
        return Location.builder()
                .fullName(recordRequestDto.getFullName())
                .city(recordRequestDto.getCity())
                .country(recordRequestDto.getCountry())
                .district(recordRequestDto.getDistrict())
                .latitude(recordRequestDto.getLatitude())
                .longitude(recordRequestDto.getLongitude())
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
