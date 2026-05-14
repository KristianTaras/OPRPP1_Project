package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.Category;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.mapper.CategoryMapper;

import java.sql.Connection;

public class CategoryRepository extends Repository<Category> {
    public CategoryRepository(Connection connection) {
        super("category", new CategoryMapper(), connection);
    }
}
