package com.be_no2_assignment.lv3_6.user.controller;

import com.be_no2_assignment.lv3_6.common.exception.BadInputException;
import com.be_no2_assignment.lv3_6.user.dto.request.UserRegisterReqDTO;
import com.be_no2_assignment.lv3_6.user.dto.request.UserUpdateReqDTO;
import com.be_no2_assignment.lv3_6.user.dto.response.UserResDTO;
import com.be_no2_assignment.lv3_6.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
  public ResponseEntity<UserResDTO> registerUser(@Valid @RequestBody UserRegisterReqDTO userRegisterReqDTO) {
    log.info("사용자 가입 요청");

    return ResponseEntity.ok(userService.registerUser(userRegisterReqDTO));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<UserResDTO> updateUser(
      @PathVariable Long id,
      HttpServletRequest request,
      @Valid @RequestBody UserUpdateReqDTO userUpdateReqDTO
  ) {
    log.info("id {} 에 해당하는 사용자 수정 요청", id);

    String passwd = request.getHeader("passwd");
    if (passwd == null) throw new BadInputException("비밀번호를 입력해주세요.");

    return ResponseEntity.ok(userService.updateUser(id, passwd, userUpdateReqDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable Long id, HttpServletRequest request) {
    log.info("id {} 에 해당하는 사용자 삭제 요청", id);

    String passwd = request.getHeader("passwd");
    if (passwd == null) throw new BadInputException("비밀번호를 입력해주세요.");

    userService.deleteUser(id, passwd);

    return ResponseEntity.ok("사용자 삭제에 성공하였습니다.");
  }
}
