package com.be_no2_assignment.lv3_6.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// lv6
public record ScheduleCreateReqDTO(
    @NotBlank(message = "할 일을 입력해주세요.")
    @Size(max = 200, message = "최대 200글자까지 가능합니다.")
    String todo,
    String createdDateTime,
    @NotNull(message = "작성자의 id를 입력해주세요.")
    Long userId)
{}
