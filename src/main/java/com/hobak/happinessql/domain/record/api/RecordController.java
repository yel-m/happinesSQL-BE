package com.hobak.happinessql.domain.record.api;



import com.hobak.happinessql.domain.record.application.RecordCalendarListService;
import com.hobak.happinessql.domain.record.application.RecordCalendarDetailService;
import com.hobak.happinessql.domain.record.application.RecordCreateService;
import com.hobak.happinessql.domain.record.application.RecordPagingService;
import com.hobak.happinessql.domain.record.converter.RecordConverter;
import com.hobak.happinessql.domain.record.dto.RecordCalendarResponseDto;
import com.hobak.happinessql.domain.record.dto.RecordCreateRequestDto;
import com.hobak.happinessql.domain.record.dto.RecordCreateResponseDto;
import com.hobak.happinessql.domain.record.dto.RecordResponseDto;
import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final RecordCalendarListService recordCalendarListService;
    private final RecordCalendarDetailService recordCalendarDetailService;
    private final UserFindService userFindService;

    @Operation(summary = "행복 기록 추가", description = "행복 기록을 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DataResponseDto<RecordCreateResponseDto> createRecord(
            @Valid @RequestPart(value="content") RecordCreateRequestDto requestDto,
            @RequestPart(required = false) MultipartFile img,
            @AuthenticationPrincipal UserDetails userDetails
        ) {
        User user = userFindService.findByUserDetails(userDetails);
        Long userId = user.getUserId();
        Long recordId = recordCreateService.createRecord(userId, requestDto, img);
        RecordCreateResponseDto recordCreationResponseDto = RecordConverter.toRecordCreateResponseDto(recordId);

        return DataResponseDto.of(recordCreationResponseDto, "행복 기록이 저장되었습니다.");
    }

    @Operation(summary = "행복 톺아보기", description = "유저가 갖고 있는 행복 기록을 무한 스크롤 방식으로 조회합니다. lastRecordId를 지정하지 않으면 자동으로 최신 게시물을 size만큼 가져옵니다.",
            parameters = {@Parameter(name="lastRecordId", description="현재까지 페이지에 그려진 게시물 id 중 가장 작은 값 (최신순이므로 아래로 내려갈수록 id값이 적어집니다.)"),
                    @Parameter(name="size", description = "한 번에 가져올 레코드의 개수")
    })
    @GetMapping
    public DataResponseDto<List<RecordResponseDto>> getRecordList(@RequestParam(required = false) Long lastRecordId, @RequestParam int size, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        Long userId = user.getUserId();
        List<RecordResponseDto> responseDtos = recordPagingService.fetchRecordPagesBy(lastRecordId, size, userId);
        return DataResponseDto.of(responseDtos, "행복 기록을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "행복 달력 조회", description = "쿼리 파타미터로 지정한 year, month에 해당하는 행복 기록 평균을 날짜별로 조회합니다. \n 이 때 행복지수 평균은 1~5 사이의 값으로 나타냅니다.",
            parameters = {@Parameter(name="year", description = "year를 지정하지 않으면 오늘 날짜 기준으로 자동 설정됩니다."),
                    @Parameter(name="month", description = "month를 지정하지 않으면 오늘 날짜 기준으로 자동 설정됩니다.")
    })
    @GetMapping("/calendar")
    public DataResponseDto<List<RecordCalendarResponseDto>> getRecordCalenderList(@RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        Long userId = user.getUserId();
        List<RecordCalendarResponseDto> responseDtos = recordCalendarListService.getHappinessAverages(month, year, userId);
        return DataResponseDto.of(responseDtos, "행복 달력을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "행복 달력 상세 조회", description = "패스 파라미터로 받은 날짜에 해당하는 모든 행복 기록을 조회합니다.",
            parameters = {@Parameter(name="date", description = "format : yyyy-mm-dd")
    })
    @GetMapping("/calendar/{date}")
    public DataResponseDto<List<RecordResponseDto>> getRecordCalenderList(@PathVariable String date, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        Long userId = user.getUserId();
        List<RecordResponseDto> responseDtos = recordCalendarDetailService.getRecords(date, userId);
        return DataResponseDto.of(responseDtos, "행복 달력을 성공적으로 조회했습니다.");
    }





}
