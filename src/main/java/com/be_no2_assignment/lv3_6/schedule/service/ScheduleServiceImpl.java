package com.be_no2_assignment.lv3_6.schedule.service;

import com.be_no2_assignment.lv3_6.common.exception.BadInputException;
import com.be_no2_assignment.lv3_6.common.exception.FailedToSaveException;
import com.be_no2_assignment.lv3_6.common.exception.DataNotFoundException;
import com.be_no2_assignment.lv3_6.schedule.domain.Schedule;
import com.be_no2_assignment.lv3_6.schedule.dto.request.*;
import com.be_no2_assignment.lv3_6.schedule.dto.response.SchedulePageResDTO;
import com.be_no2_assignment.lv3_6.schedule.dto.response.ScheduleResDTO;
import com.be_no2_assignment.lv3_6.schedule.repository.ScheduleRepository;
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

  @Override
  @Transactional
  public ScheduleResDTO createSchedule(ScheduleCreateReqDTO scheduleCreateReqDTO) {
    Schedule schedule;
    Long userId = scheduleCreateReqDTO.userId();

/* lv6에서 아래 값들의 존재 여부를 검증하고 있으므로 주석처리
 *    if (userId == null) {
 *      throw new BadInputException("작성자의 id를 입력해주세요.");
 *    }
 *
 *    if (scheduleCreateReqDTO.todo() == null) {
 *      throw new BadInputException("할 일을 입력해주세요.");
 *    }
 */

    if (scheduleCreateReqDTO.createdDateTime() == null) {
      schedule = new Schedule(scheduleCreateReqDTO.todo(), userId);
    } else {
      schedule = new Schedule(
          scheduleCreateReqDTO.todo(),
          parseDateTime(scheduleCreateReqDTO.createdDateTime()),
          userId);
    }

    return scheduleRepository.findScheduleById(scheduleRepository.save(schedule))
        .map(ScheduleResDTO::toDTO)
        .orElseThrow(() -> new FailedToSaveException("일정 추가 중 오류가 발생하였습니다."));
  }

  @Override
  @Transactional(readOnly = true)
  public ScheduleResDTO findScheduleById(Long id) {
    return scheduleRepository.findScheduleById(id)
        .map(ScheduleResDTO::toDTO)
//        .orElseThrow(() -> new RuntimeException("해당 일정을 찾을 수 없습니다."));
        .orElseThrow(() -> new DataNotFoundException("해당 일정을 찾을 수 없습니다."));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ScheduleResDTO> findAllSchedules(ScheduleFindReqDTO scheduleFindReqDTO) {
    Long userId = scheduleFindReqDTO.userId();
    LocalDate updatedDateTime = scheduleFindReqDTO.updatedDate();

    return scheduleRepository.findAllSchedules(userId, updatedDateTime).stream()
        .map(ScheduleResDTO::toDTO)
        .toList();
  }

  private Timestamp parseDateTime(String dateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return Timestamp.valueOf(LocalDateTime.parse(dateTime, formatter));
  }

  @Override
  @Transactional
  public ScheduleResDTO updateSchedule(Long id, ScheduleUpdateReqDTO scheduleUpdateReqDTO) {
    return scheduleRepository.findScheduleById(
            scheduleRepository.updateSchedule(
                id,
                scheduleUpdateReqDTO.todo(),
                new Timestamp(System.currentTimeMillis()
                )
            )
        )
        .map(ScheduleResDTO::toDTO)
//        .orElseThrow(() -> new RuntimeException("수정된 일정을 찾을 수 없습니다."));
        .orElseThrow(() -> new DataNotFoundException("수정된 일정을 찾을 수 없습니다."));
  }

  @Override
  @Transactional
  public void deleteSchedule(Long id) {
    scheduleRepository.deleteSchedule(id);
  }

  // lv4
  @Override
  @Transactional(readOnly = true)
  public List<SchedulePageResDTO> findSchedulePages(SchedulePageReqDTO schedulePageReqDTO) {
    int offset = (schedulePageReqDTO.pageNum() - 1) * schedulePageReqDTO.pageSize();

    return scheduleRepository.findAllSchedulesWithUsername(schedulePageReqDTO.pageSize(), offset);
  }
}
