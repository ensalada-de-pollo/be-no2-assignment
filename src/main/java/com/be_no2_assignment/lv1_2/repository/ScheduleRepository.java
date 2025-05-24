package com.be_no2_assignment.lv1_2.repository;

import com.be_no2_assignment.lv1_2.domain.Schedule;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

public interface ScheduleRepository {
  // lv1
  Long save(Schedule schedule);
  List<Schedule> findAllSchedules(String username, LocalDate updatedDateTime);
  Optional<Schedule> findScheduleById(Long id);

  // lv2
  Long updateSchedule(Long id, String todo, Timestamp updatedDateTime);
  void deleteSchedule(Long id);
}
