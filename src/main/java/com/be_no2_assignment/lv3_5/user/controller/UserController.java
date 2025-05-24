package com.be_no2_assignment.lv3_5.user.controller;

import com.be_no2_assignment.lv3_5.user.dto.request.UserDeleteReqDTO;
import com.be_no2_assignment.lv3_5.user.dto.request.UserRegisterReqDTO;
import com.be_no2_assignment.lv3_5.user.dto.request.UserUpdateReqDTO;
import com.be_no2_assignment.lv3_5.user.dto.response.UserResDTO;
import com.be_no2_assignment.lv3_5.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<UserResDTO> getUserById(@PathVariable Long id) {
    log.info("id {} 에 해당하는 사용자 찾기 요청", id);

    return ResponseEntity.ok(userService.findUserById(id));
  }

  @PostMapping("/register")
  public ResponseEntity<UserResDTO> registerUser(@RequestBody UserRegisterReqDTO userRegisterReqDTO) {
    log.info("사용자 가입 요청");

    return ResponseEntity.ok(userService.registerUser(userRegisterReqDTO));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateReqDTO userUpdateReqDTO) {
    log.info("id {} 에 해당하는 사용자 수정 요청", id);

    return ResponseEntity.ok(userService.updateUser(id, userUpdateReqDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable Long id, @RequestBody UserDeleteReqDTO userDeleteReqDTO) {
    log.info("id {} 에 해당하는 사용자 삭제 요청", id);

    userService.deleteUser(id, userDeleteReqDTO);

    return ResponseEntity.ok().build();
  }
}
