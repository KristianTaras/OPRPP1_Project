package hr.algebra.model.mapper;

import hr.algebra.model.entities.*;
import hr.algebra.model.interfaces.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SmartWatchMapper implements RowMapper<SmartWatch> {

    private final BrandMapper brandMapper = new BrandMapper();
    private final CategoryMapper categoryMapper = new CategoryMapper();
    private final OperatingSystemMapper operatingSystemMapper = new OperatingSystemMapper();

    @Override
    public SmartWatch map(ResultSet rs) throws SQLException {

        return new SmartWatch(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("year"),
                rs.getDouble("screenSize"),
                rs.getInt("batteryLife"),
                rs.getString("ipRating"),
                rs.getDouble("price"),
                rs.getString("imagePath"),
                brandMapper.map(rs),
                categoryMapper.map(rs),
                operatingSystemMapper.map(rs)
        );
    }
}
