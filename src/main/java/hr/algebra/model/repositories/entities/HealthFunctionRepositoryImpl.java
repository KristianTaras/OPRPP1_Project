package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.mapper.HealthFunctionMapper;
import hr.algebra.model.repositories.HealthFunctionRepository;

import java.sql.Connection;

public class HealthFunctionRepositoryImpl extends RepositoryImpl<HealthFunction> implements HealthFunctionRepository {
    public HealthFunctionRepositoryImpl(Connection connection) {
        super("health_function", new HealthFunctionMapper(), connection);
    }
}
