package com.be_no2_assignment.lv3_6.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Schedule {
  @Id
  private Long id;
  private String todo;
  private Timestamp createdDateTime;
  private Timestamp updatedDateTime;
  private Long userId;

  public Schedule(String todo, Timestamp createdDateTime, Long userId) {
    this.todo = todo;
    this.createdDateTime = createdDateTime;
    this.updatedDateTime = this.createdDateTime;
    this.userId = userId;
  }

  public Schedule(String todo, Long userId) {
    this.todo = todo;
    this.createdDateTime = new Timestamp(System.currentTimeMillis());
    this.updatedDateTime = this.createdDateTime;
    this.userId = userId;
  }

  public void setTodo(String todo) {
    this.todo = todo;
  }

  public void setUpdatedDateTime(Timestamp updatedDateTime) {
    this.updatedDateTime = updatedDateTime;
  }
}
