package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.OperatingSystem;
import hr.algebra.model.mapper.OperatingSystemMapper;

import java.sql.Connection;

public class OperatingSystemRepository extends Repository<OperatingSystem> {
    public OperatingSystemRepository(Connection connection) {
        super("operating_system", new OperatingSystemMapper(), connection);
    }
}
