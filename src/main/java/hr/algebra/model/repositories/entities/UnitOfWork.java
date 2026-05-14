package hr.algebra.model.repositories.entities;

import hr.algebra.database.Database;
import hr.algebra.model.repositories.interfaces.UnitOfWorkInterface;

import java.sql.Connection;
import java.sql.SQLException;

public class UnitOfWork implements UnitOfWorkInterface, AutoCloseable {

    private final Connection connection;

    private SmartWatchRepository smartWatchRepository;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private OperatingSystemRepository operatingSystemRepository;
    private HealthFunctionRepository healthFunctionRepository;


    public UnitOfWork() throws SQLException{
        connection = Database.getConnection();
        connection.setAutoCommit(false); //Turn off to enable rollback
    }

    //Lazy loading
    @Override
    public SmartWatchRepository getSmartWatchRepository() { //Make private
        if(smartWatchRepository == null){
            smartWatchRepository = new SmartWatchRepository(connection);
        }
        return smartWatchRepository;
    }

    @Override
    public BrandRepository getBrandRepository() {
        if(brandRepository == null){
            brandRepository = new BrandRepository(connection);
        }
        return brandRepository;
    }

    @Override
    public CategoryRepository getCategoryRepository() {
        if(categoryRepository == null){
            categoryRepository = new CategoryRepository(connection);
        }
        return categoryRepository;
    }

    @Override
    public OperatingSystemRepository getOperatingSystemRepository() {
        if(operatingSystemRepository == null) {
            operatingSystemRepository = new OperatingSystemRepository(connection);
        }
        return operatingSystemRepository;
    }

    @Override
    public HealthFunctionRepository getHealthFunctionRepository() {
        if(healthFunctionRepository == null) {
            healthFunctionRepository = new HealthFunctionRepository(connection);
        }
        return healthFunctionRepository;
    }

    @Override
    public void commit() throws SQLException{
        if(connection != null) connection.commit();
    }

    @Override
    public void close() throws Exception {
        if(connection != null){
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Override
    public void rollback() throws SQLException {
        try {
            if(connection != null){
                connection.rollback();
            }
        }catch(SQLException e ) { //custom exception
            //Throw new Custom exception
        }
    }
}
