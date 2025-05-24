package com.be_no2_assignment.lv3_6.schedule.repository;

import com.be_no2_assignment.lv3_6.schedule.domain.Schedule;
import com.be_no2_assignment.lv3_6.schedule.dto.response.SchedulePageResDTO;
import com.be_no2_assignment.lv3_6.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
  @Query("SELECT s FROM Schedule s WHERE date(s.updatedDateTime) = :updatedDate order by s.updatedDateTime desc")
  List<Schedule> findAllByUpdatedDateTime(LocalDate updatedDate);
  @Query("SELECT s FROM Schedule s WHERE s.user.id = :userId AND date(s.createdDateTime) = :updatedDate order by s.updatedDateTime desc")
  List<Schedule> findAllByUserIdAndUpdatedDateTime(Long userId, LocalDate updatedDate);
  @Query("SELECT s FROM Schedule s WHERE s.user.id = :userId order by s.updatedDateTime desc")
  List<Schedule> findAllByUserId(Long userId);
  @Query("SELECT s.user.id FROM Schedule s WHERE s.id = :id")
  Optional<Long> findUserIdById(Long id);
  void deleteAllByUser(User user);
  // lv4
  @Query(
      value = """
          SELECT new com.be_no2_assignment.lv3_6.schedule.dto.response.SchedulePageResDTO
                    (s.id, s.todo, s.createdDateTime, s.updatedDateTime, u.id, u.username)
          FROM Schedule s INNER JOIN s.user u
          """)
  Page<SchedulePageResDTO> findAllPage(Pageable pageable);
}
