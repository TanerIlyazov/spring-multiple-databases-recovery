package com.tanerilyazov.test.multipledatasourcesrecovery.mapper;

import com.tanerilyazov.test.multipledatasourcesrecovery.domain.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Taner - Delta Source Bulgaria on 9.07.21.
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setAge(rs.getInt("age"));
        user.setId(rs.getInt("id"));

        return user;
    }
}
