package com.be_no2_assignment.lv3_6.user.service;

import com.be_no2_assignment.lv3_6.user.dto.request.UserRegisterReqDTO;
import com.be_no2_assignment.lv3_6.user.dto.request.UserUpdateReqDTO;
import com.be_no2_assignment.lv3_6.user.dto.response.UserResDTO;

public interface UserService {
  UserResDTO registerUser(UserRegisterReqDTO userRegisterReqDTO);
  void checkPassword(Long id, String passwd);
  UserResDTO findUserById(Long id);
  UserResDTO updateUser(Long id, String passwd, UserUpdateReqDTO userUpdateReqDTO);
  void deleteUser(Long id, String passwd);
}
