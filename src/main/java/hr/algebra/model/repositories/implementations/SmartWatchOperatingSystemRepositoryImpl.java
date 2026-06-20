package hr.algebra.model.repositories.implementations;

import hr.algebra.model.entities.SmartWatchOperatingSystem;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.repositories.interfaces.SmartWatchOperatingSystemRepository;

import java.sql.Connection;

public class SmartWatchOperatingSystemRepositoryImpl extends RepositoryImpl<SmartWatchOperatingSystem> implements SmartWatchOperatingSystemRepository {
    public SmartWatchOperatingSystemRepositoryImpl(Connection connection, String table, RowMapper<SmartWatchOperatingSystem> mapper) {
        super(connection, table, mapper);
    }
}
