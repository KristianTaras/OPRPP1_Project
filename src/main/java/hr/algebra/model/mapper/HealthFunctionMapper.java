package hr.algebra.model.mapper;

import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.interfaces.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HealthFunctionMapper implements RowMapper<HealthFunction> {
    @Override
    public HealthFunction map(ResultSet rs) throws SQLException {
        return new HealthFunction(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description")
                //Add more parameters
        );
    }
}
