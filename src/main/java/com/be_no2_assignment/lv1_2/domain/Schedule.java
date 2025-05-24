package com.be_no2_assignment.lv1_2.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Schedule {

  @Id
  private Long id;
  private String username;
  private String passwd;
  private String todo;
  private Timestamp createdDateTime;
  private Timestamp updatedDateTime;

  public Schedule(String username, String passwd, String todo) {
    this.username = username;
    this.passwd = passwd;
    this.todo = todo;
    this.createdDateTime = new Timestamp(System.currentTimeMillis());
    this.updatedDateTime = this.createdDateTime;
  }

  public Schedule(String username, String passwd, String todo, Timestamp createdDateTime) {
    this.username = username;
    this.passwd = passwd;
    this.todo = todo;
    this.createdDateTime = createdDateTime;
    this.updatedDateTime = createdDateTime;
  }

  public void setTodo(String todo) {
    this.todo = todo;
  }

  public void setUpdatedDateTime(Timestamp updatedDateTime) {
    this.updatedDateTime = updatedDateTime;
  }
}
