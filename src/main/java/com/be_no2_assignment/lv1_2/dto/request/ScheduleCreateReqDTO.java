package com.be_no2_assignment.lv1_2.dto.request;

import lombok.Getter;

// lv1
@Getter
public class ScheduleCreateReqDTO {
  private String username;
  private String passwd;
  private String todo;
  private String createdDateTime;
}
