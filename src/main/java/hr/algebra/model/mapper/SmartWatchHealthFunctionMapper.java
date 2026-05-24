package hr.algebra.model.mapper;

import hr.algebra.model.entities.SmartWatchHealthFunction;
import hr.algebra.model.interfaces.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SmartWatchHealthFunctionMapper implements RowMapper<SmartWatchHealthFunction> {

    @Override
    public SmartWatchHealthFunction map(ResultSet rs) throws SQLException {
        return new SmartWatchHealthFunction(
                rs.getInt("id"),
                rs.getInt("smart_watch_id"),
                rs.getInt("health_function_id")
        );
    }
}
