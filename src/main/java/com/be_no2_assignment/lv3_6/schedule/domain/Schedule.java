package com.be_no2_assignment.lv3_6.schedule.domain;

import com.be_no2_assignment.lv3_6.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Entity
@NoArgsConstructor
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String todo;
  private Timestamp createdDateTime;
  private Timestamp updatedDateTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "id")
  private User user;

  public Schedule(String todo, Timestamp createdDateTime, User user) {
    this.todo = todo;
    this.createdDateTime = createdDateTime;
    this.updatedDateTime = this.createdDateTime;
    this.user = user;
  }

  public Schedule(String todo, User user) {
    this.todo = todo;
    this.createdDateTime = new Timestamp(System.currentTimeMillis());
    this.updatedDateTime = this.createdDateTime;
    this.user = user;
  }

  public void setTodo(String todo) {
    this.todo = todo;
  }

  public void setUpdatedDateTime(Timestamp updatedDateTime) {
    this.updatedDateTime = updatedDateTime;
  }
}
