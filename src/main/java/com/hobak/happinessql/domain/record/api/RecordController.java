package com.hobak.happinessql.domain.record.api;


import com.hobak.happinessql.domain.record.application.RecordCreationService;
import com.hobak.happinessql.domain.record.dto.RecordRequestDto;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.repository.UserRespository;
import com.hobak.happinessql.global.response.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/records")
public class RecordController {

    private final RecordCreationService recordService;
    private final UserRespository userRespository;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DataResponseDto<String> createRecord(
        @Valid @RequestPart(value="content") RecordRequestDto recordRequestDto,
        @RequestPart(required = false) MultipartFile img
        ) {

        // TODO: 임시값 : 로그인 / 로그아웃 구현 시 수정
        // 한 번 실행 시 임시 유저가 만들어지므로 1회 실행 후에는 이 부분은 주석 처리하고 실행하면 됩니다.
        System.out.println(recordRequestDto.getMemo());
        User user = User.builder()
                .username("hobak")
                .password("happy")
                .name("사그미")
                .age(22)
                .gender("여")
                .build();
        User newUser = userRespository.save(user);
        System.out.println(newUser.getUserId());

        recordService.createRecord(1L, recordRequestDto, img);

        return DataResponseDto.of("행복 기록이 저장되었습니다.");
    }




}
