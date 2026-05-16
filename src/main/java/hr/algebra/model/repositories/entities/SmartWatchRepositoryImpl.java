package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.mapper.SmartWatchMapper;
import hr.algebra.model.repositories.SmartWatchRepository;

import java.sql.Connection;

public class SmartWatchRepositoryImpl extends RepositoryImpl<SmartWatch> implements SmartWatchRepository {
    public SmartWatchRepositoryImpl(Connection connection) {
        super("smart_watch",new SmartWatchMapper(),connection);
    }
}
