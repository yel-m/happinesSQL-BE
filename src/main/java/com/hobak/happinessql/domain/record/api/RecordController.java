package com.hobak.happinessql.domain.record.api;


import com.hobak.happinessql.domain.record.application.RecordCreateService;
import com.hobak.happinessql.domain.record.application.RecordPagingService;
import com.hobak.happinessql.domain.record.converter.RecordConverter;
import com.hobak.happinessql.domain.record.dto.RecordCreateRequestDto;
import com.hobak.happinessql.domain.record.dto.RecordCreateResponseDto;
import com.hobak.happinessql.domain.record.dto.RecordResponseDto;
import com.hobak.happinessql.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name="Record", description = "행복기록 관련 REST API에 대한 명세를 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/records")
public class RecordController {

    private final RecordCreateService recordCreateService;
    private final RecordPagingService recordPagingService;

    @Operation(summary = "행복 기록 추가", description = "행복 기록을 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DataResponseDto<Object> createRecord(
            @Valid @RequestPart(value="content") RecordCreateRequestDto requestDto,
            @RequestPart(required = false) MultipartFile img,
            Long userId
        ) {

        Long recordId = recordCreateService.createRecord(userId, requestDto, img);
        RecordCreateResponseDto recordCreationResponseDto = RecordConverter.toRecordCreateResponseDto(recordId);

        return DataResponseDto.of(recordCreationResponseDto, "행복 기록이 저장되었습니다.");
    }

    @Operation(summary = "행복 톺아보기", description = "유저가 갖고 있는 행복 기록을 무한 스크롤 방식으로 조회합니다. lastRecordId를 지정하지 않으면 자동으로 최신 게시물을 size만큼 가져옵니다.",
            parameters = {@Parameter(name="lastRecordId", description="현재까지 페이지에 그려진 게시물 id 중 가장 작은 값 (최신순이므로 아래로 내려갈수록 id값이 적어집니다.)"),
                    @Parameter(name="size", description = "한 번에 가져올 레코드의 개수")
    })
    @GetMapping
    public DataResponseDto<Object> getRecordList(@RequestParam(required = false) Long lastRecordId, @RequestParam int size, Long userId) {
        List<RecordResponseDto> responseDtos = recordPagingService.fetchRecordPagesBy(lastRecordId, size, userId);
        return DataResponseDto.of(responseDtos, "행복 기록을 성공적으로 조회했습니다.");
    }





}
