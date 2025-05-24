package com.be_no2_assignment.lv3_6.user.service;

import com.be_no2_assignment.lv3_6.common.exception.BadInputException;
import com.be_no2_assignment.lv3_6.common.exception.FailedToSaveException;
import com.be_no2_assignment.lv3_6.common.exception.DataNotFoundException;
import com.be_no2_assignment.lv3_6.common.exception.PasswordMismatchException;
import com.be_no2_assignment.lv3_6.schedule.repository.ScheduleRepository;
import com.be_no2_assignment.lv3_6.user.domain.User;
import com.be_no2_assignment.lv3_6.user.dto.request.UserRegisterReqDTO;
import com.be_no2_assignment.lv3_6.user.dto.request.UserUpdateReqDTO;
import com.be_no2_assignment.lv3_6.user.dto.response.UserResDTO;
import com.be_no2_assignment.lv3_6.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  // 사용자 삭제에 앞서 사용자와 관련된 일정 우선 삭제를 위한 일정 레포지토리 객체
  private final ScheduleRepository scheduleRepository;

  // 각 메서드에 주석처리된 예외는 lv5 구현 이전 예외처리입니다.
  @Override
  @Transactional
  public UserResDTO registerUser(UserRegisterReqDTO userRegisterReqDTO) {
    Timestamp now = new Timestamp(System.currentTimeMillis());

//    if (userRegisterReqDTO.passwd() == null) {
//      throw new BadInputException("비밀번호를 입력해주세요."); // lv6에서 값의 존재 여부를 검증하고 있으므로 주석처리
//    }

    User user = new User(
        userRegisterReqDTO.username(),
        userRegisterReqDTO.passwd(),
        userRegisterReqDTO.email(),
        now,
        now
    );

    return userRepository.findUserById(userRepository.save(user))
        .map(UserResDTO::toDTO)
//        .orElseThrow(() -> new RuntimeException("사용자 생성에 실패하였습니다."));
        .orElseThrow(() -> new FailedToSaveException("생성에 실패하였습니다."));
  }

  @Override
  @Transactional(readOnly = true)
  public UserResDTO findUserById(Long id) {
    return userRepository.findUserById(id)
        .map(UserResDTO::toDTO)
//        .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
        .orElseThrow(() -> new DataNotFoundException("해당 사용자를 찾을 수 없습니다."));
  }

  @Override
  @Transactional
  public UserResDTO updateUser(Long id, UserUpdateReqDTO userUpdateReqDTO) {
    return userRepository.findUserById(
            userRepository.updateUser(id,
                userUpdateReqDTO.username(),
                userUpdateReqDTO.email(),
                new Timestamp(System.currentTimeMillis())
        ))
        .map(UserResDTO::toDTO)
//        .orElseThrow(() -> new RuntimeException("수정된 사용자를 찾을 수 없습니다."));
        .orElseThrow(() -> new DataNotFoundException("수정된 사용자를 찾을 수 없습니다."));
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    scheduleRepository.deleteScheduleByUserId(id); // 참조 무결성 원칙
    userRepository.deleteUser(id);
  }

  @Override
  public void checkPassword(Long id, String passwd) {
    userRepository.findUserById(id)
        .map(user -> {
          if (!user.getPasswd().equals(passwd)) {
            log.info("user password {}", user.getPasswd());
            log.info("input password {}", passwd);
//            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
          }
          return user;
        })
//        .orElseThrow(() -> new RuntimeException("사용자를 찾는 중에 문제가 발생하였습니다."));
        .orElseThrow(() -> new DataNotFoundException("해당 사용자를 찾을 수 없습니다."));
  }
}
