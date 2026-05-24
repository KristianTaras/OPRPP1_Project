package hr.algebra.model.mapper;

import hr.algebra.model.entities.Category;
import hr.algebra.model.interfaces.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category map(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
