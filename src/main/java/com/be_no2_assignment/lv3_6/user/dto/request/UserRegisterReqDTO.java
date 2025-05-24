package com.be_no2_assignment.lv3_6.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// lv6
public record UserRegisterReqDTO(
    String username,
    @NotBlank(message = "비밀번호는 필수입니다.") String passwd,
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    String email) {}
