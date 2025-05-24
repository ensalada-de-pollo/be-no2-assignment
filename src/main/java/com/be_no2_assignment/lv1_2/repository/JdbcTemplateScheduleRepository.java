package com.be_no2_assignment.lv1_2.repository;

import com.be_no2_assignment.lv1_2.domain.Schedule;
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

  // lv1
  @Override
  public Long save(Schedule schedule) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

    Map<String, Object> parameters = new HashMap<>();

    parameters.put("username", schedule.getUsername());
    parameters.put("passwd", schedule.getPasswd());
    parameters.put("todo", schedule.getTodo());
    parameters.put("createdDateTime", schedule.getCreatedDateTime());
    parameters.put("updatedDateTime", schedule.getUpdatedDateTime()); // 처음 생성시 수정일은 작성일과 동일

    Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

    return key.longValue();
  }

  @Override
  public List<Schedule> findAllSchedules(String username, LocalDate updatedDateTime) {
    StringBuilder sql = new StringBuilder("select * from schedule where 1=1"); // 여러 조건이 포함될 경우, sql문을 덧붙이는 방식으로 조건문 작성
    List<Object> params = new ArrayList<>();

    if (username != null && !username.isEmpty()) { // 쿼리 파라미터에 username이 존재하는 경우
      sql.append(" and username = ?");
      params.add(username);
    }

    if (updatedDateTime != null) { // 쿼리 파라미터에 updatedDateTime이 존재하는 경우
      sql.append(" and date(updatedDateTime) = date(?)"); // 시간을 제외한 날짜만을 가지고 비교를 하므로 date 함수 적용
      params.add(updatedDateTime);
    }

    sql.append(" order by updatedDateTime desc"); // 수정일을 기준으로 내림차순 정렬

    return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), params.toArray());
  }

  @Override
  public Optional<Schedule> findScheduleById(Long id) {
    List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapper(), id);

    return result.stream().findAny();
  }

  private RowMapper<Schedule> scheduleRowMapper() {
    return (rs, rowNum) -> new Schedule(
        rs.getLong("id"),
        rs.getString("username"),
        rs.getString("passwd"),
        rs.getString("todo"),
        rs.getTimestamp("createdDateTime"),
        rs.getTimestamp("updatedDateTime")
    );
  }

  // lv2
  @Override
  public Long updateSchedule(Long id, String todo, Timestamp updatedDateTime) {
    StringBuilder stringBuilder = new StringBuilder("update schedule set ");
    List<Object> params = new ArrayList<>();

    if (todo != null) {
      stringBuilder.append("todo = ?, ");
      params.add(todo);
    }

    stringBuilder.append("updatedDateTime = ? where id = ?");
    params.add(updatedDateTime);
    params.add(id);

    int result = jdbcTemplate.update(stringBuilder.toString(), params.toArray());

    if (result == 0) {
      throw new RuntimeException("일정 수정에서 오류가 발생하였습니다.");
    }

    return id;
  }

  @Override
  public void deleteSchedule(Long id) {
    jdbcTemplate.update("delete from schedule where id = ?", id);
  }
}
