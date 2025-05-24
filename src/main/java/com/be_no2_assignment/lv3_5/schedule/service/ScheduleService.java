package com.be_no2_assignment.lv3_5.schedule.service;

import com.be_no2_assignment.lv3_5.schedule.dto.request.ScheduleCreateReqDTO;
import com.be_no2_assignment.lv3_5.schedule.dto.request.ScheduleDeleteReqDTO;
import com.be_no2_assignment.lv3_5.schedule.dto.request.ScheduleFindReqDTO;
import com.be_no2_assignment.lv3_5.schedule.dto.request.ScheduleUpdateReqDTO;
import com.be_no2_assignment.lv3_5.schedule.dto.response.ScheduleResDTO;

import java.util.List;

public interface ScheduleService {
  ScheduleResDTO createSchedule(ScheduleCreateReqDTO scheduleCreateReqDTO);
  List<ScheduleResDTO> findAllSchedules(ScheduleFindReqDTO scheduleFindReqDTO);
  ScheduleResDTO findScheduleById(Long id);

  // lv2
  ScheduleResDTO updateSchedule(Long id, ScheduleUpdateReqDTO scheduleUpdateReqDTO);
  void deleteSchedule(Long id, ScheduleDeleteReqDTO scheduleDeleteReqDTO);
}
