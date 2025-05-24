package com.be_no2_assignment.lv3_5.schedule.repository;

import com.be_no2_assignment.lv3_5.schedule.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateScheduleRepository implements ScheduleRepository {
  private final JdbcTemplate jdbcTemplate;

  @Override
  public Long save(Schedule schedule) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

    Map<String, Object> parameters = new HashMap<>();

    parameters.put("todo", schedule.getTodo());
    parameters.put("createdDateTime", schedule.getCreatedDateTime());
    parameters.put("updatedDateTime", schedule.getCreatedDateTime());
    parameters.put("userId", schedule.getUserId());

    Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

    return key.longValue();
  }

  @Override
  public List<Schedule> findAllSchedules(Long userId, LocalDate updatedDateTime) {
    StringBuilder sql = new StringBuilder("select * from schedule where 1=1");
    List<Object> params = new ArrayList<>();

    if (userId != null) {
      sql.append(" and userId = ?");
      params.add(userId);
    }

    if (updatedDateTime != null) {
      sql.append(" and date(updatedDateTime) = ?");
      params.add(updatedDateTime);
    }

    sql.append(" order by updatedDateTime desc");

    return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), params.toArray());
  }

  @Override
  public Optional<Schedule> findScheduleById(Long id) {
    List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapper(), id);

    return result.stream().findAny();
  }

  @Override
  public Long updateSchedule(Long id, String todo, Timestamp updatedDateTime) {
    int result = jdbcTemplate.update("update schedule set todo = ?, updatedDateTime = ? where id = ?",
        todo, updatedDateTime, id);

    if (result == 0) {
      throw new RuntimeException("일정 수정에서 오류가 발생하였습니다.");
    }

    return id;
  }

  @Override
  public void deleteSchedule(Long id) {
    jdbcTemplate.update("delete from schedule where id = ?", id);
  }

  @Override
  public void deleteScheduleByUserId(Long userId) {
    jdbcTemplate.update("delete from schedule where userId = ?", userId);
  }

  private RowMapper<Schedule> scheduleRowMapper() {
    return (rs, rowNum) -> new Schedule(
        rs.getLong("id"),
        rs.getString("todo"),
        rs.getTimestamp("createdDateTime"),
        rs.getTimestamp("updatedDateTime"),
        rs.getLong("userId")
    );
  }
}
