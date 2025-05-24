package com.be_no2_assignment.lv3_6.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

// lv6
public record UserUpdateReqDTO(
    String username,
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    String email) {}
