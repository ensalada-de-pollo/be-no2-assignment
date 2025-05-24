package com.be_no2_assignment.lv3_6.schedule.dto.response;

import com.be_no2_assignment.lv3_6.schedule.domain.Schedule;

import java.sql.Timestamp;

public record ScheduleResDTO(Long id, String todo, Timestamp createdDateTime, Timestamp updatedDateTime, Long userId){

  public static ScheduleResDTO toDTO(Schedule schedule) {
    return new ScheduleResDTO(schedule.getId(),
        schedule.getTodo(),
        schedule.getCreatedDateTime(),
        schedule.getUpdatedDateTime(),
        schedule.getUserId());
  }
}
