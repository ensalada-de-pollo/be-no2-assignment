package com.be_no2_assignment.lv3_6.common;

import org.springframework.http.HttpStatus;

public record ErrorResult(HttpStatus statusCode, String errorMessage) {
}
