package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.Brand;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.mapper.BrandMapper;
import hr.algebra.model.repositories.BrandRepository;

import java.sql.Connection;

public class BrandRepositoryImpl extends RepositoryImpl<Brand> implements BrandRepository {

    public BrandRepositoryImpl(Connection connection, String table, RowMapper<Brand> mapper) {
        super(connection, table, mapper);
    }
}
