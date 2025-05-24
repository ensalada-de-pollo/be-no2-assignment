package com.be_no2_assignment.lv3_6.schedule.dto.request;

import jakarta.validation.constraints.Min;

// lv4
// lv6
public record SchedulePageReqDTO(
    @Min(value = 1, message = "최솟값은 1입니다.") int pageNum,
    @Min(value = 1, message = "최솟값은 1입니다.") int pageSize) {}
