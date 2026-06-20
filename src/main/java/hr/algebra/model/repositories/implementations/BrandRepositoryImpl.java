package hr.algebra.model.repositories.implementations;

import hr.algebra.model.entities.Brand;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.repositories.interfaces.BrandRepository;

import java.sql.Connection;

public class BrandRepositoryImpl extends RepositoryImpl<Brand> implements BrandRepository {

    public BrandRepositoryImpl(Connection connection, String table, RowMapper<Brand> mapper) {
        super(connection, table, mapper);
    }
}
