package hr.algebra.model.repositories.entities;

import hr.algebra.database.Database;
import hr.algebra.model.repositories.*;

import java.sql.Connection;
import java.sql.SQLException;

public class UnitOfWorkImpl implements UnitOfWork, AutoCloseable {

    private final Connection connection;

    private SmartWatchRepository smartWatchRepository;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private OperatingSystemRepository operatingSystemRepository;
    private HealthFunctionRepository healthFunctionRepository;
    private UserRepository userRepository;


    public UnitOfWorkImpl() throws SQLException{
        connection = Database.getConnection();
        connection.setAutoCommit(false); //Turn off to enable rollback
    }

    //Lazy loading
    @Override
    public SmartWatchRepository getSmartWatchRepository() {
        if(smartWatchRepository == null){
            smartWatchRepository = new SmartWatchRepositoryImpl(connection);
        }
        return smartWatchRepository;
    }

    @Override
    public BrandRepository getBrandRepository() {
        if(brandRepository == null){
            brandRepository = new BrandRepositoryImpl(connection);
        }
        return brandRepository;
    }

    @Override
    public CategoryRepository getCategoryRepository() {
        if(categoryRepository == null){
            categoryRepository = new CategoryRepositoryImpl(connection);
        }
        return categoryRepository;
    }

    @Override
    public OperatingSystemRepository getOperatingSystemRepository() {
        if(operatingSystemRepository == null) {
            operatingSystemRepository = new OperatingSystemRepositoryImpl(connection);
        }
        return operatingSystemRepository;
    }

    @Override
    public HealthFunctionRepository getHealthFunctionRepository() {
        if(healthFunctionRepository == null) {
            healthFunctionRepository = new HealthFunctionRepositoryImpl(connection);
        }
        return healthFunctionRepository;
    }

    @Override
    public UserRepository getUserRepository() {
        if(userRepository == null){
            userRepository = new UserRepositoryImpl(connection);
        }
        return userRepository;
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
