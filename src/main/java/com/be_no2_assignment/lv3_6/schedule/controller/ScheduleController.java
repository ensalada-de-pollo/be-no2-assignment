package com.be_no2_assignment.lv3_6.schedule.controller;

import com.be_no2_assignment.lv3_6.common.exception.BadInputException;
import com.be_no2_assignment.lv3_6.schedule.dto.request.*;
import com.be_no2_assignment.lv3_6.schedule.dto.response.SchedulePageResDTO;
import com.be_no2_assignment.lv3_6.schedule.dto.response.ScheduleResDTO;
import com.be_no2_assignment.lv3_6.schedule.service.ScheduleService;
import com.be_no2_assignment.lv3_6.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
@Slf4j
public class ScheduleController {
  private final ScheduleService scheduleService;
  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<ScheduleResDTO> findScheduleById(@PathVariable Long id) {
    log.info("Find schedule by id: {}", id);

    return ResponseEntity.ok(scheduleService.findScheduleById(id));
  }

  @GetMapping("/all")
  public ResponseEntity<List<ScheduleResDTO>> findAllSchedules(ScheduleFindReqDTO scheduleFindReqDTO) {
    log.info("Find all schedules");

    return ResponseEntity.ok(scheduleService.findAllSchedules(scheduleFindReqDTO));
  }

  @PostMapping("/create")
  public ResponseEntity<ScheduleResDTO> createSchedule(@Valid @RequestBody ScheduleCreateReqDTO scheduleCreateReqDTO) {
    log.info("Add schedule: {}", scheduleCreateReqDTO);

    return ResponseEntity.ok(scheduleService.createSchedule(scheduleCreateReqDTO));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ScheduleResDTO> updateSchedule(
      @PathVariable Long id,
      HttpServletRequest request,
      @Valid @RequestBody ScheduleUpdateReqDTO scheduleUpdateReqDTO
  ) {
    log.info("schedule id {} 에 대한 수정 요청입니다.", id);

    String passwd = request.getHeader("passwd");
    if (passwd == null) throw new BadInputException("비밀번호를 입력해주세요.");
    userService.checkPassword(id, passwd);

    return ResponseEntity.ok(scheduleService.updateSchedule(id, scheduleUpdateReqDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteSchedule(@PathVariable Long id, HttpServletRequest request) {
    log.info("schedule id {} 에 대한 삭제 요청입니다.", id);

    String passwd = request.getHeader("passwd");
    if (passwd == null) throw new BadInputException("비밀번호를 입력해주세요.");
    userService.checkPassword(id, passwd);

    scheduleService.deleteSchedule(id);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/page")
  public ResponseEntity<List<SchedulePageResDTO>> findSchedulePages(@Valid SchedulePageReqDTO schedulePageReqDTO) {
    log.info("Find schedule pages");

    return ResponseEntity.ok(scheduleService.findSchedulePages(schedulePageReqDTO));
  }
}
