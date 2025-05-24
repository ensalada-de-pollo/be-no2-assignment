package com.be_no2_assignment.lv3_5.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class User {
  @Id
  private Long id;
  private String username;
  private String passwd;
  private String email;
  private Timestamp registedDateTime;
  private Timestamp updatedDateTime;

  public User(String username, String passwd, String email, Timestamp registedDateTime, Timestamp updatedDateTime) {
    this.username = username;
    this.passwd = passwd;
    this.email = email;
    this.registedDateTime = registedDateTime;
    this.updatedDateTime = updatedDateTime;
  }

  public void setUpdatedDateTime(Timestamp updatedDateTime) {
    this.updatedDateTime = updatedDateTime;
  }
}
