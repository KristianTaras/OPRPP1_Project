package hr.algebra.model.repositories.interfaces;

import hr.algebra.model.entities.SmartWatch;

import java.sql.SQLException;
import java.util.List;

public interface SmartWatchRepository extends Repository<SmartWatch> {

    SmartWatch getFullById(int id) throws Exception;

    List<SmartWatch> getFullAll() throws Exception;

    void linkHealthFunction(int smartwatchId, int healthFunctionId) throws SQLException;
    void linkOperatingSystem(int smartwatchId, int operatingSystemId) throws SQLException;

    void deleteById(int id) throws Exception;
    void deleteSmartWatchHealthFunctions(int smartWatchId) throws SQLException;
    void deleteSmartWatchOperatingSystem(int smartWatchId) throws SQLException;

}
