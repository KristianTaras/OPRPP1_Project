package hr.algebra.model.mapper;

import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.security.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("phoneNumber"),
                rs.getString("passwordHash"),
                rs.getClass()
        );
    }
}
