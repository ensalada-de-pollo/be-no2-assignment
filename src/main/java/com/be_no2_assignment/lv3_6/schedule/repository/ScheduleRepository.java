package com.be_no2_assignment.lv3_6.schedule.repository;

import com.be_no2_assignment.lv3_6.schedule.domain.Schedule;
import com.be_no2_assignment.lv3_6.schedule.dto.response.SchedulePageResDTO;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
  Long save(Schedule schedule);
  List<Schedule> findAllSchedules(Long userId, LocalDate updatedDateTime);
  Optional<Schedule> findScheduleById(Long id);
  Long updateSchedule(Long id, String todo, Timestamp updatedDateTime);
  void deleteSchedule(Long id);
  void deleteScheduleByUserId(Long userId);

  // lv4
  List<SchedulePageResDTO> findAllSchedulesWithUsername(int size, int offset);
}
