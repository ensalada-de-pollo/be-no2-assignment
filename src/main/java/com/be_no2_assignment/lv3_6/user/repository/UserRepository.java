package com.be_no2_assignment.lv3_6.user.repository;

import com.be_no2_assignment.lv3_6.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserById(Long id);
}
