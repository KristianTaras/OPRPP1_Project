package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.Category;
import hr.algebra.model.mapper.CategoryMapper;
import hr.algebra.model.repositories.CategoryRepository;

import java.sql.Connection;

public class CategoryRepositoryImpl extends RepositoryImpl<Category> implements CategoryRepository {
    public CategoryRepositoryImpl(Connection connection) {
        super("category", new CategoryMapper(), connection);
    }
}
