package hr.algebra.model.mapper;

import hr.algebra.model.entities.SmartWatchOperatingSystem;
import hr.algebra.model.interfaces.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SmartWatchOperatingSystemMapper implements RowMapper<SmartWatchOperatingSystem> {
    @Override
    public SmartWatchOperatingSystem map(ResultSet rs) throws SQLException {
        return new SmartWatchOperatingSystem(
                rs.getInt("id"),
                rs.getInt("smart_watch_id"),
                rs.getInt("operating_system_id")
        );
    }
}
