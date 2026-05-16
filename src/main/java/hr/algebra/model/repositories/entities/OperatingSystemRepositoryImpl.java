package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.OperatingSystem;
import hr.algebra.model.mapper.OperatingSystemMapper;
import hr.algebra.model.repositories.OperatingSystemRepository;

import java.sql.Connection;

public class OperatingSystemRepositoryImpl extends RepositoryImpl<OperatingSystem> implements OperatingSystemRepository {
    public OperatingSystemRepositoryImpl(Connection connection) {
        super("operating_system", new OperatingSystemMapper(), connection);
    }
}
