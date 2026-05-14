package hr.algebra.model.repositories.interfaces;

import hr.algebra.model.repositories.entities.*;

import java.sql.SQLException;

public interface UnitOfWorkInterface {

    SmartWatchRepository getSmartWatchRepository();
    BrandRepository getBrandRepository();
    CategoryRepository getCategoryRepository();
    OperatingSystemRepository getOperatingSystemRepository();
    HealthFunctionRepository getHealthFunctionRepository();

    void commit() throws SQLException;

    void close() throws Exception;

    void rollback() throws SQLException;
}
