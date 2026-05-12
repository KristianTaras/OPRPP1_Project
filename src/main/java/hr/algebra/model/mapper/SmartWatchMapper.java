package hr.algebra.model.mapper;

import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.interfaces.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SmartWatchMapper implements RowMapper<SmartWatch> {
    @Override
    public SmartWatch map(ResultSet rs) throws SQLException {
        return new SmartWatch(
                rs.getInt("id"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getDouble("price")
                //Extend the properties after extending the constructor
        );
    }
}
