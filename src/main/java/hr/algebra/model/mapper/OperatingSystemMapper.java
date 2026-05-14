package hr.algebra.model.mapper;

import hr.algebra.model.entities.OperatingSystem;
import hr.algebra.model.interfaces.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperatingSystemMapper implements RowMapper<OperatingSystem> {

    @Override
    public OperatingSystem map(ResultSet rs) throws SQLException {
        return new OperatingSystem(
                rs.getInt("id")
                //Add more parameters
        );
    }
}
