package hr.algebra.model.repositories.entities;

import hr.algebra.database.Database;
import hr.algebra.model.repositories.interfaces.UnitOfWorkInterface;

import java.sql.Connection;
import java.sql.SQLException;

public class UnitOfWork implements UnitOfWorkInterface, AutoCloseable {

    private final Connection connection;

    private SmartWatchRepository smartWatchRepository;

    public UnitOfWork() throws SQLException{
        this.connection = Database.getConnection();
    }

    @Override
    public SmartWatchRepository getSmartWatchRepository() {
        if(smartWatchRepository == null){
            smartWatchRepository = new SmartWatchRepository(connection);
        }
        return smartWatchRepository;
    }

    @Override
    public void commit() throws SQLException{
        connection.commit();
    }

    @Override
    public void close() throws Exception {
        if(connection != null) connection.close();
    }
}
