package com.be_no2_assignment.lv3_5.user.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateReqDTO {
  private String username;
  private String passwd;
  private String email;
}
