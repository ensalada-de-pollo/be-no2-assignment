package com.be_no2_assignment.lv3_5.schedule.dto.response;

import com.be_no2_assignment.lv3_5.schedule.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class ScheduleResDTO {
  private Long id;
  private String todo;
  private Timestamp createdDateTime;
  private Timestamp updatedDateTime;
  private Long userId;

  public ScheduleResDTO(Schedule schedule) {
    this.id = schedule.getId();
    this.todo = schedule.getTodo();
    this.createdDateTime = schedule.getCreatedDateTime();
    this.updatedDateTime = schedule.getUpdatedDateTime();
    this.userId = schedule.getUserId();
  }
}
