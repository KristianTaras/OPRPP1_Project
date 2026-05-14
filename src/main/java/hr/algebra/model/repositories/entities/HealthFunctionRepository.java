package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.mapper.HealthFunctionMapper;

import java.sql.Connection;

public class HealthFunctionRepository extends Repository<HealthFunction> {
    public HealthFunctionRepository(Connection connection) {
        super("health_function", new HealthFunctionMapper(), connection);
    }
}
