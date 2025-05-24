package com.be_no2_assignment.lv3_6.schedule.service;

import com.be_no2_assignment.lv3_6.schedule.dto.request.*;
import com.be_no2_assignment.lv3_6.schedule.dto.response.SchedulePageResDTO;
import com.be_no2_assignment.lv3_6.schedule.dto.response.ScheduleResDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScheduleService {
  ScheduleResDTO createSchedule(ScheduleCreateReqDTO scheduleCreateReqDTO);
  List<ScheduleResDTO> findAllSchedules(ScheduleFindReqDTO scheduleFindReqDTO);
  ScheduleResDTO findScheduleById(Long id);
  ScheduleResDTO updateSchedule(Long id, String passwd, ScheduleUpdateReqDTO scheduleUpdateReqDTO);
  void deleteSchedule(Long id, String passwd);

  // lv4
  Page<SchedulePageResDTO> findSchedulePages(SchedulePageReqDTO schedulePageReqDTO);
}
