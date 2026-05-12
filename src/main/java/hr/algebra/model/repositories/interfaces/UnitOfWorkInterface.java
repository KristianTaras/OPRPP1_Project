package hr.algebra.model.repositories.interfaces;

import hr.algebra.model.repositories.entities.SmartWatchRepository;

import java.sql.SQLException;

public interface UnitOfWorkInterface {

    SmartWatchRepository getSmartWatchRepository();

    void commit() throws SQLException;

    void close() throws Exception;
}
