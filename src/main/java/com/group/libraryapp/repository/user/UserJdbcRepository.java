package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(String name, int age) {
        String insertSql = "INSERT INTO user (name, age) VALUES (?, ?)";
        jdbcTemplate.update(insertSql, name, age);
    }

    public List<UserResponse> readUsers() {
        String selectSql = "SELECT * FROM user";
        return jdbcTemplate.query(selectSql, (rs, roNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });
    }

    public void updateUserName(String name, long id) {
        String updateSql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, name, id);
    }

    public void deleteUser(String name) {
        String deleteSql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(deleteSql, name);
    }

    public boolean isUserNotExist(long id) {
        String readSql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.query(readSql, (rs, roNum) -> 0, id).isEmpty();
    }

    public boolean isUserNotExist(String name) {
        String readSql = "SELECT * FROM user WHERE name = ?";
        return jdbcTemplate.query(readSql, (rs, roNum) -> 0, name).isEmpty();
    }
}
