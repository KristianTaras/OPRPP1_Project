package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.SmartWatch;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.mapper.SmartWatchMapper;

import java.sql.Connection;

public class SmartWatchRepository extends Repository<SmartWatch> {
    public SmartWatchRepository(Connection connection) {
        super("smart_watch",new SmartWatchMapper(),connection);
    }
}
