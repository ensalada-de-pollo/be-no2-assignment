package com.be_no2_assignment.lv1_2.service;

import com.be_no2_assignment.lv1_2.domain.Schedule;
import com.be_no2_assignment.lv1_2.dto.request.ScheduleCreateReqDTO;
import com.be_no2_assignment.lv1_2.dto.request.ScheduleDeleteReqDTO;
import com.be_no2_assignment.lv1_2.dto.request.ScheduleFindReqDTO;
import com.be_no2_assignment.lv1_2.dto.request.ScheduleUpdateReqDTO;
import com.be_no2_assignment.lv1_2.dto.response.ScheduleResDTO;
import com.be_no2_assignment.lv1_2.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

  private final ScheduleRepository scheduleRepository;

  // lv1 service 코드입니다.
  @Override
  @Transactional
  public ScheduleResDTO createSchedule(ScheduleCreateReqDTO scheduleCreateReqDTO) {
    Schedule schedule;

    if (scheduleCreateReqDTO.getCreatedDateTime() == null) { // 작성일을 따로 설정하지 않은 경우
      schedule = new Schedule(scheduleCreateReqDTO.getUsername(), scheduleCreateReqDTO.getPasswd(), scheduleCreateReqDTO.getTodo());
    } else {
      schedule = new Schedule(scheduleCreateReqDTO.getUsername(), // 작성일을 설정한 경우
          scheduleCreateReqDTO.getPasswd(),
          scheduleCreateReqDTO.getTodo(),
          parseDateTime(scheduleCreateReqDTO.getCreatedDateTime()));
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
    String username = scheduleFindReqDTO.getUsername();
    LocalDate updatedDateTime = scheduleFindReqDTO.getUpdatedDateTime();

    return scheduleRepository.findAllSchedules(username, updatedDateTime).stream()
        .map(ScheduleResDTO::new)
        .toList();
  }

  private Timestamp parseDateTime(String dateTime) { // String으로 들어온 DateTime을 Timestamp로 변환하는 메서드
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return Timestamp.valueOf(LocalDateTime.parse(dateTime, formatter));
  }

  // lv2 service 코드입니다.
  @Override
  @Transactional
  public ScheduleResDTO updateSchedule(Long id, ScheduleUpdateReqDTO scheduleUpdateReqDTO) {
    checkPassword(id, scheduleUpdateReqDTO.getPasswd());

    return scheduleRepository.findScheduleById(
        scheduleRepository.updateSchedule(id,
            scheduleUpdateReqDTO.getPasswd(),
            scheduleUpdateReqDTO.getTodo(),
            new Timestamp(System.currentTimeMillis()))
        ) // update시 수정날짜는 수정 시점으로 지정
        .map(ScheduleResDTO::new)
        .orElseThrow(() -> new RuntimeException("수정된 일정을 찾을 수 없습니다."));
  }

  @Override
  @Transactional
  public void deleteSchedule(Long id) {
    scheduleRepository.deleteSchedule(id);
  }

  @Override
  public void checkPassword(Long id, String password) { // update와 delete에서 사용할 비밀번호 확인 로직
    scheduleRepository.findScheduleById(id)
        .map(schedule -> {
          if (!schedule.getPasswd().equals(password)) {
            throw new InputMismatchException("비밀번호가 일치하지 않습니다.");
          }
          return schedule;
        }).orElseThrow(() -> new RuntimeException("일정을 찾는 중 오류가 발생하였습니다."));
  }
}
