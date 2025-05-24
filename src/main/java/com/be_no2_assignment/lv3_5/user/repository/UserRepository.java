package com.be_no2_assignment.lv3_5.user.repository;

import com.be_no2_assignment.lv3_5.user.domain.User;

import java.sql.Timestamp;
import java.util.Optional;

public interface UserRepository {
  Long save(User user);
  Optional<User> findUserById(Long id);

  Long updateUser(Long id, String username, String email, Timestamp updatedDateTime);
  void deleteUser(Long id);
}
