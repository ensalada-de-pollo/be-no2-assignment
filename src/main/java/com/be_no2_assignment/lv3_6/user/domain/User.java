package com.be_no2_assignment.lv3_6.user.domain;

import com.be_no2_assignment.lv3_6.schedule.domain.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String passwd;
  private String email;
  private Timestamp registedDateTime;
  private Timestamp updatedDateTime;

  @OneToMany(mappedBy = "user")
  private final List<Schedule> schedules = new ArrayList<>();

  public User(String username, String passwd, String email, Timestamp registedDateTime, Timestamp updatedDateTime) {
    this.username = username;
    this.passwd = passwd;
    this.email = email;
    this.registedDateTime = registedDateTime;
    this.updatedDateTime = updatedDateTime;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setUpdatedDateTime(Timestamp updatedDateTime) {
    this.updatedDateTime = updatedDateTime;
  }

}
