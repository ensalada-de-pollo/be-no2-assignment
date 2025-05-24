package com.be_no2_assignment.lv3_6.schedule.dto.request;

import java.time.LocalDate;

public record ScheduleFindReqDTO(Long id, Long userId, LocalDate updatedDate) {}
