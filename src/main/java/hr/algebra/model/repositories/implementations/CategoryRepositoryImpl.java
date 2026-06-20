package hr.algebra.model.repositories.implementations;

import hr.algebra.model.entities.Category;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.repositories.interfaces.CategoryRepository;

import java.sql.Connection;

public class CategoryRepositoryImpl extends RepositoryImpl<Category> implements CategoryRepository {
    public CategoryRepositoryImpl(Connection connection, String table, RowMapper<Category> mapper) {
        super(connection, table, mapper);
    }
}
