package com.be_no2_assignment.lv3_6.schedule.dto.response;

import java.sql.Timestamp;

public record SchedulePageResDTO(Long id, String todo, Timestamp createdDateTime, Timestamp updatedDateTime, Long userId, String username) {}