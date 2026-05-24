package hr.algebra.model.mapper;

import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.entities.Role;
import hr.algebra.model.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getString("username"),
                rs.getString("password_hash"),
                Role.valueOf(rs.getString("Role"))
        );
    }
}
