package com.be_no2_assignment.lv3_5.schedule.dto.request;

import lombok.Getter;

@Getter
public class ScheduleCreateReqDTO {
  private String username;
  private String passwd;
  private String todo;
  private String createdDateTime;
  private Long userId;
}
