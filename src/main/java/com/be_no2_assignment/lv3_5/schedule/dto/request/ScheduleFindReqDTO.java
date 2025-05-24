package com.be_no2_assignment.lv3_5.schedule.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ScheduleFindReqDTO {
  private Long id;
  private Long userId;
  private LocalDate updatedDateTime;
}
