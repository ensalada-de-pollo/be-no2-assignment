package com.be_no2_assignment.lv3_5.schedule.service;

import com.be_no2_assignment.lv3_5.schedule.domain.Schedule;
import com.be_no2_assignment.lv3_5.schedule.dto.request.ScheduleCreateReqDTO;
import com.be_no2_assignment.lv3_5.schedule.dto.request.ScheduleDeleteReqDTO;
import com.be_no2_assignment.lv3_5.schedule.dto.request.ScheduleFindReqDTO;
import com.be_no2_assignment.lv3_5.schedule.dto.request.ScheduleUpdateReqDTO;
import com.be_no2_assignment.lv3_5.schedule.dto.response.ScheduleResDTO;
import com.be_no2_assignment.lv3_5.schedule.repository.ScheduleRepository;
import com.be_no2_assignment.lv3_5.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
  private final ScheduleRepository scheduleRepository;
  private final UserService userService;

  @Override
  @Transactional
  public ScheduleResDTO createSchedule(ScheduleCreateReqDTO scheduleCreateReqDTO) {
    Schedule schedule;
    Long userId = scheduleCreateReqDTO.getUserId();

    if (scheduleCreateReqDTO.getCreatedDateTime() == null) {
      schedule = new Schedule(scheduleCreateReqDTO.getTodo(), userId);
    } else {
      schedule = new Schedule(
          scheduleCreateReqDTO.getTodo(),
          parseDateTime(scheduleCreateReqDTO.getCreatedDateTime()),
          userId);
    }

    return scheduleRepository.findScheduleById(scheduleRepository.save(schedule))
        .map(ScheduleResDTO::new)
        .orElseThrow(() -> new RuntimeException("일정 추가 중 오류가 발생하였습니다."));
  }

  @Override
  @Transactional(readOnly = true)
  public ScheduleResDTO findScheduleById(Long id) {
    return scheduleRepository.findScheduleById(id)
        .map(ScheduleResDTO::new)
        .orElseThrow(() -> new RuntimeException("해당 일정을 찾을 수 없습니다."));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ScheduleResDTO> findAllSchedules(ScheduleFindReqDTO scheduleFindReqDTO) {
    Long userId = scheduleFindReqDTO.getUserId();
    LocalDate updatedDateTime = scheduleFindReqDTO.getUpdatedDateTime();

    return scheduleRepository.findAllSchedules(userId, updatedDateTime).stream()
        .map(ScheduleResDTO::new)
        .toList();
  }

  private Timestamp parseDateTime(String dateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return Timestamp.valueOf(LocalDateTime.parse(dateTime, formatter));
  }

  @Override
  @Transactional
  public ScheduleResDTO updateSchedule(Long id, ScheduleUpdateReqDTO scheduleUpdateReqDTO) {
    userService.checkPassword(id, scheduleUpdateReqDTO.getPasswd());

    return scheduleRepository.findScheduleById(
            scheduleRepository.updateSchedule(
                id,
                scheduleUpdateReqDTO.getTodo(),
                new Timestamp(System.currentTimeMillis()
                )
            )
        )
        .map(ScheduleResDTO::new)
        .orElseThrow(() -> new RuntimeException("수정된 일정을 찾을 수 없습니다."));
  }

  @Override
  @Transactional
  public void deleteSchedule(Long id, ScheduleDeleteReqDTO scheduleDeleteReqDTO) {
    userService.checkPassword(id, scheduleDeleteReqDTO.getPasswd());
    scheduleRepository.deleteSchedule(id);
  }
}
