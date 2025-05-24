package com.be_no2_assignment.lv3_6.schedule.service;

import com.be_no2_assignment.lv3_6.common.exception.DataNotFoundException;
import com.be_no2_assignment.lv3_6.schedule.domain.Schedule;
import com.be_no2_assignment.lv3_6.schedule.dto.request.*;
import com.be_no2_assignment.lv3_6.schedule.dto.response.SchedulePageResDTO;
import com.be_no2_assignment.lv3_6.schedule.dto.response.ScheduleResDTO;
import com.be_no2_assignment.lv3_6.schedule.repository.ScheduleRepository;
import com.be_no2_assignment.lv3_6.user.domain.User;
import com.be_no2_assignment.lv3_6.user.repository.UserRepository;
import com.be_no2_assignment.lv3_6.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  private final UserRepository userRepository;
  private final UserService userService;

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
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new DataNotFoundException("해당 사용자를 찾을 수 없습니다."));

    if (scheduleCreateReqDTO.createdDateTime() == null) {
      schedule = new Schedule(scheduleCreateReqDTO.todo(), user);
    } else {
      schedule = new Schedule(
          scheduleCreateReqDTO.todo(),
          parseDateTime(scheduleCreateReqDTO.createdDateTime()),
          user);
    }

    return ScheduleResDTO.toDTO(scheduleRepository.save(schedule));
  }

  @Override
  @Transactional(readOnly = true)
  public ScheduleResDTO findScheduleById(Long id) {
    return scheduleRepository.findById(id)
        .map(ScheduleResDTO::toDTO)
//        .orElseThrow(() -> new RuntimeException("해당 일정을 찾을 수 없습니다."));
        .orElseThrow(() -> new DataNotFoundException("해당 일정을 찾을 수 없습니다."));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ScheduleResDTO> findAllSchedules(ScheduleFindReqDTO scheduleFindReqDTO) {
    Long userId = scheduleFindReqDTO.userId();
    LocalDate updatedDate = scheduleFindReqDTO.updatedDate();

    if (userId != null && updatedDate != null) {
      return scheduleRepository.findAllByUserIdAndUpdatedDateTime(userId, updatedDate).stream()
          .map(ScheduleResDTO::toDTO)
          .toList();
    } else if (userId != null) {
      return scheduleRepository.findAllByUserId(userId).stream()
          .map(ScheduleResDTO::toDTO)
          .toList();
    } else if (updatedDate != null) {
      return scheduleRepository.findAllByUpdatedDateTime(updatedDate).stream()
          .map(ScheduleResDTO::toDTO)
          .toList();
    }

    return scheduleRepository.findAll(Sort.by(Sort.Order.desc("updatedDateTime"))).stream()
        .map(ScheduleResDTO::toDTO)
        .toList();
  }

  @Override
  @Transactional
  public ScheduleResDTO updateSchedule(Long id, String passwd, ScheduleUpdateReqDTO scheduleUpdateReqDTO) {
    Long userId = scheduleRepository.findUserIdById(id)
        .orElseThrow(() -> new DataNotFoundException("해당 일정을 찾을 수 없습니다."));
    userService.checkPassword(userId, passwd);

    return scheduleRepository.findById(id)
        .map(schedule -> {
          schedule.setTodo(scheduleUpdateReqDTO.todo());
          schedule.setUpdatedDateTime(new Timestamp(System.currentTimeMillis()));

          return scheduleRepository.save(schedule);
        })
        .map(ScheduleResDTO::toDTO)
//        .orElseThrow(() -> new RuntimeException("수정된 일정을 찾을 수 없습니다."));
        .orElseThrow(() -> new DataNotFoundException("수정된 일정을 찾을 수 없습니다."));
  }

  @Override
  @Transactional
  public void deleteSchedule(Long id, String passwd) {
    Long userId = scheduleRepository.findUserIdById(id)
        .orElseThrow(() -> new DataNotFoundException("해당 일정을 찾을 수 없습니다."));
    userService.checkPassword(userId, passwd);

    scheduleRepository.deleteById(id);
  }

  private Timestamp parseDateTime(String dateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
    return Timestamp.valueOf(localDateTime);
  }

  // lv4
  @Override
  @Transactional(readOnly = true)
  public Page<SchedulePageResDTO> findSchedulePages(SchedulePageReqDTO schedulePageReqDTO) {
    Pageable pageable = PageRequest.of(
        schedulePageReqDTO.pageNum(),
        schedulePageReqDTO.pageSize(), Sort.by("updatedDateTime").descending());

    return scheduleRepository.findAllPage(pageable);
  }
}
