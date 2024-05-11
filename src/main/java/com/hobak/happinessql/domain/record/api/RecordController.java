package com.hobak.happinessql.domain.record.api;


import com.hobak.happinessql.domain.record.application.RecordCreateService;
import com.hobak.happinessql.domain.record.application.RecordPagingService;
import com.hobak.happinessql.domain.record.converter.RecordConverter;
import com.hobak.happinessql.domain.record.dto.RecordCreateRequestDto;
import com.hobak.happinessql.domain.record.dto.RecordCreateResponseDto;
import com.hobak.happinessql.domain.record.dto.RecordResponseDto;
import com.hobak.happinessql.domain.user.domain.Gender;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Operation(summary = "행복 기록 추가", description = "행복 기록을 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DataResponseDto<Object> createRecord(
            @Valid @RequestPart(value="content") RecordCreateRequestDto requestDto,
            @RequestPart(required = false) MultipartFile img
        ) {

        // TODO: 임시값 : 로그인 / 로그아웃 구현 시 수정
        // 한 번 실행 시 임시 유저가 만들어지므로 1회 실행 후에는 이 부분은 주석 처리하고 실행하면 됩니다.
        System.out.println(requestDto.getMemo());
        User user = User.builder()
                .username("hobak")
                .password("happy")
                .name("사그미")
                .age(22)
                .gender(Gender.FEMALE)
                .build();
        User newUser = userRepository.save(user);
        System.out.println("userId : " + newUser.getUserId());

        Long recordId = recordCreateService.createRecord(newUser.getUserId(), requestDto, img);
        RecordCreateResponseDto recordCreationResponseDto = RecordConverter.toRecordCreateResponseDto(recordId);

        return DataResponseDto.of(recordCreationResponseDto, "행복 기록이 저장되었습니다.");
    }

    @Operation(summary = "행복 톺아보기", description = "유저가 갖고 있는 행복 기록을 무한 스크롤 방식으로 조회합니다.",
            parameters = {@Parameter(name="lastRecordId", description="현재까지 페이지에 그려진 게시물 id 중 가장 작은 값 (최신순이므로 아래로 내려갈수록 id값이 적어집니다.)"),
                    @Parameter(name="size", description = "한 번에 가져올 레코드의 개수")
    })
    @GetMapping
    public DataResponseDto<Object> getRecordList(@RequestParam Long lastRecordId, @RequestParam int size) {
        // TODO : 임시값 -> 로그인한 유저의 id를 찾아내는 로직으로 변경
        Long userId = 1L;
        List<RecordResponseDto> responseDtos = recordPagingService.fetchRecordPagesBy(lastRecordId, size, userId);
        return DataResponseDto.of(responseDtos, "행복 기록을 성공적으로 조회했습니다.");
    }





}
