package com.be_no2_assignment.lv3_5.user.dto.response;

import com.be_no2_assignment.lv3_5.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class UserResDTO {
  private Long id;
  private String username;
  private String email;
  private Timestamp registerDateTime;
  private Timestamp updatedDateTime;

  public UserResDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.registerDateTime = user.getRegistedDateTime();
    this.updatedDateTime = user.getUpdatedDateTime();
  }
}
