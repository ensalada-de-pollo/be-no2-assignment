package com.be_no2_assignment.lv3_6.user.repository;

import com.be_no2_assignment.lv3_6.common.exception.FailedToSaveException;
import com.be_no2_assignment.lv3_6.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateUserRepository implements UserRepository {
  private final JdbcTemplate jdbcTemplate;

  @Override
  public Long save(User user) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

    Map<String, Object> parameters = new HashMap<>();

    parameters.put("username", user.getUsername());
    parameters.put("passwd", user.getPasswd());
    parameters.put("email", user.getEmail());
    parameters.put("registerDateTime", user.getRegistedDateTime());
    parameters.put("updatedDateTime", user.getUpdatedDateTime());

    Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

    return key.longValue();
  }

  @Override
  public Optional<User> findUserById(Long id) {
      List<User> result = jdbcTemplate.query("select * from user where id = ?", UserRowMapper(), id);

      return result.stream().findAny();
  }

  @Override
  public Long updateUser(Long id, String username, String email, Timestamp updatedDateTime) {
    StringBuilder sql = new StringBuilder("update user set");
    List<Object> params = new ArrayList<>();

    if (username != null) {
      sql.append(" username = ?,");
      params.add(username);
    }

    if (email != null) {
      sql.append(" email = ?,");
      params.add(email);
    }

    sql.append(" updatedDateTime = ? where id = ?");
    params.add(updatedDateTime);
    params.add(id);

    int result = jdbcTemplate.update(sql.toString(), params.toArray());

    if (result == 0) {
      throw new FailedToSaveException("일정 수정에서 오류가 발생하였습니다.");
    }

    return id;
  }

  @Override
  public void deleteUser(Long id) {
    jdbcTemplate.update("delete from user where id = ?", id);
  }

  private RowMapper<User> UserRowMapper() {
    return (rs, rowNum) -> (new User(
        rs.getLong("id"),
        rs.getString("username"),
        rs.getString("passwd"),
        rs.getString("email"),
        rs.getTimestamp("registerDateTime"),
        rs.getTimestamp("updatedDateTime")
      )
    );
  }
}
