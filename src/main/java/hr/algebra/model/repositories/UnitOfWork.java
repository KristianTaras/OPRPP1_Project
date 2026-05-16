package hr.algebra.model.repositories;

import hr.algebra.model.repositories.entities.*;

import java.sql.SQLException;

public interface UnitOfWork {

    SmartWatchRepository getSmartWatchRepository();
    BrandRepository getBrandRepository();
    CategoryRepository getCategoryRepository();
    OperatingSystemRepository getOperatingSystemRepository();
    HealthFunctionRepository getHealthFunctionRepository();
    UserRepository getUserRepository();

    void commit() throws SQLException;

    void close() throws Exception;

    void rollback() throws SQLException;
}
