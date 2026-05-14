package hr.algebra.model.mapper;

import hr.algebra.model.entities.Brand;
import hr.algebra.model.interfaces.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BrandMapper implements RowMapper<Brand> {
    @Override
    public Brand map(ResultSet rs) throws SQLException {
        return new Brand(
                rs.getInt("id")
                //Add more parameters
        );
    }
}
