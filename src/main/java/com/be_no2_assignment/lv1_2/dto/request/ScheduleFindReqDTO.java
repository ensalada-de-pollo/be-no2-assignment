package com.be_no2_assignment.lv1_2.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// lv1
@Setter // 쿼리 파라미터로 데이터를 받기 위한 setter
@Getter
public class ScheduleFindReqDTO {
  private Long id;
  private String username;
  private LocalDate updatedDateTime;
}
