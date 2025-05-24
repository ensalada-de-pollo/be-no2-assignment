package com.be_no2_assignment.lv1_2.dto.response;

import com.be_no2_assignment.lv1_2.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

// lv1
@Getter
@AllArgsConstructor
public class ScheduleResDTO {
  private Long id;
  private String username;
  private String todo;
  private Timestamp createdDateTime;
  private Timestamp updatedDateTime;

  public ScheduleResDTO(Schedule schedule) {
    this.id = schedule.getId();
    this.username = schedule.getUsername();
    this.todo = schedule.getTodo();
    this.createdDateTime = schedule.getCreatedDateTime();
    this.updatedDateTime = schedule.getUpdatedDateTime();
  }
}
