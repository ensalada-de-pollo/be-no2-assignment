package com.be_no2_assignment.lv1_2.service;

import com.be_no2_assignment.lv1_2.dto.request.ScheduleCreateReqDTO;
import com.be_no2_assignment.lv1_2.dto.request.ScheduleFindReqDTO;
import com.be_no2_assignment.lv1_2.dto.request.ScheduleUpdateReqDTO;
import com.be_no2_assignment.lv1_2.dto.response.ScheduleResDTO;

import java.util.*;

public interface ScheduleService {
  // lv1
  ScheduleResDTO createSchedule(ScheduleCreateReqDTO scheduleCreateReqDTO);
  List<ScheduleResDTO> findAllSchedules(ScheduleFindReqDTO scheduleFindReqDTO);
  ScheduleResDTO findScheduleById(Long id);

  // lv2
  ScheduleResDTO updateSchedule(Long id, ScheduleUpdateReqDTO scheduleUpdateReqDTO);
  void deleteSchedule(Long id);
  void checkPassword(Long id, String passwd);
}
