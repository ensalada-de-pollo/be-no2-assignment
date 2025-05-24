package com.be_no2_assignment.lv1_2.controller;

import com.be_no2_assignment.lv1_2.dto.request.ScheduleCreateReqDTO;
import com.be_no2_assignment.lv1_2.dto.request.ScheduleDeleteReqDTO;
import com.be_no2_assignment.lv1_2.dto.request.ScheduleFindReqDTO;
import com.be_no2_assignment.lv1_2.dto.request.ScheduleUpdateReqDTO;
import com.be_no2_assignment.lv1_2.dto.response.ScheduleResDTO;
import com.be_no2_assignment.lv1_2.service.ScheduleService;
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

  // lv1
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
  public ResponseEntity<ScheduleResDTO> createSchedule(@RequestBody ScheduleCreateReqDTO scheduleCreateReqDTO) {
    log.info("Add schedule: {}", scheduleCreateReqDTO);

    return ResponseEntity.ok(scheduleService.createSchedule(scheduleCreateReqDTO));
  }

  // lv2
  @PutMapping("/{id}")
  public ResponseEntity<ScheduleResDTO> updateSchedule(@PathVariable Long id, @RequestBody ScheduleUpdateReqDTO scheduleUpdateReqDTO) {
    log.info("schedule id {} 에 대한 수정 요청입니다.", id);

    return ResponseEntity.ok(scheduleService.updateSchedule(id, scheduleUpdateReqDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleDeleteReqDTO scheduleDeleteReqDTO) {
    log.info("schedule id {} 에 대한 삭제 요청입니다.", id);

    scheduleService.deleteSchedule(id, scheduleDeleteReqDTO);

    return ResponseEntity.ok().build();
  }
}
