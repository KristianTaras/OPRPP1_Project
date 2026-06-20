package hr.algebra.model.repositories.implementations;


import hr.algebra.model.mapper.*;
import hr.algebra.model.repositories.interfaces.*;
import hr.algebra.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.SQLException;

public class UnitOfWorkImpl implements UnitOfWork, AutoCloseable {

    private final Connection connection;

    private SmartWatchRepository smartWatchRepository;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private OperatingSystemRepository operatingSystemRepository;
    private HealthFunctionRepository healthFunctionRepository;
    private SmartWatchHealthFunctionRepository smartWatchHealthFunctionRepository;
    private SmartWatchOperatingSystemRepository smartWatchOperatingSystemRepository;
    private UserRepository userRepository;


    public UnitOfWorkImpl() throws SQLException{
        connection = DatabaseUtil.getConnection();
        connection.setAutoCommit(false); //Turn off to enable rollback
    }

    public Connection getConnection(){
        return connection;
    }

    //Lazy loading
    @Override
    public SmartWatchRepository getSmartWatchRepository() {
        if(smartWatchRepository == null){
            smartWatchRepository = new SmartWatchRepositoryImpl(connection,"smartwatch",
                    new SmartWatchMapper(),
                    getSmartWatchHealthFunctionRepository(),
                    getOperatingSystemRepository(),
                    getHealthFunctionRepository());
        }
        return smartWatchRepository;
    }

    @Override
    public BrandRepository getBrandRepository() {
        if(brandRepository == null){
            brandRepository = new BrandRepositoryImpl(connection, "brand", new BrandMapper());
        }
        return brandRepository;
    }

    @Override
    public CategoryRepository getCategoryRepository() {
        if(categoryRepository == null){
            categoryRepository = new CategoryRepositoryImpl(connection, "category", new CategoryMapper());
        }
        return categoryRepository;
    }

    @Override
    public OperatingSystemRepository getOperatingSystemRepository() {
        if(operatingSystemRepository == null) {
            operatingSystemRepository = new OperatingSystemRepositoryImpl(connection, "operatingSystem", new OperatingSystemMapper());
        }
        return operatingSystemRepository;
    }

    @Override
    public HealthFunctionRepository getHealthFunctionRepository() {
        if(healthFunctionRepository == null) {
            healthFunctionRepository = new HealthFunctionRepositoryImpl(connection, "healthFunction", new HealthFunctionMapper());
        }
        return healthFunctionRepository;
    }

    @Override
    public SmartWatchHealthFunctionRepository getSmartWatchHealthFunctionRepository() {
        if(smartWatchHealthFunctionRepository == null){
            smartWatchHealthFunctionRepository = new SmartWatchHealthFunctionRepositoryImpl(connection, "SmartWatchHealthFunction", new SmartWatchHealthFunctionMapper());
        }
        return smartWatchHealthFunctionRepository;
    }

    @Override
    public SmartWatchOperatingSystemRepository getSmartWatchOperatingSystemRepository() {
        if(smartWatchOperatingSystemRepository == null){
            smartWatchOperatingSystemRepository = new SmartWatchOperatingSystemRepositoryImpl(connection, "SmartWatchOperatingSystem", new SmartWatchOperatingSystemMapper());
        }
        return smartWatchOperatingSystemRepository;
    }


    @Override
    public UserRepository getUserRepository() throws SQLException {
        if(userRepository == null){
            userRepository = new UserRepositoryImpl(connection, "user", new UserMapper());
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
        }catch(SQLException e ) {
            throw new SQLException();
        }
    }
}
