package com.be_no2_assignment.lv3_6.user.dto.response;

import com.be_no2_assignment.lv3_6.user.domain.User;

import java.sql.Timestamp;

public record UserResDTO(Long id, String username, String email, Timestamp registerDateTime, Timestamp updatedDateTime) {

  public static UserResDTO toDTO(User user) {
    return new UserResDTO(user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getRegistedDateTime(),
        user.getUpdatedDateTime()
    );
  }
}
