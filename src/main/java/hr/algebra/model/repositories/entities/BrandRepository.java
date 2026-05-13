package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.Brand;
import hr.algebra.model.mapper.BrandMapper;

import java.sql.Connection;

public class BrandRepository extends Repository<Brand> {

    public BrandRepository(Connection connection) {
        super("brand", new BrandMapper(), connection);
    }
}
