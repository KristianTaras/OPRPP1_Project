package hr.algebra.model.repositories;

import hr.algebra.model.entities.SmartWatch;

import java.sql.SQLException;
import java.util.List;

public interface SmartWatchRepository extends Repository<SmartWatch> {

    SmartWatch getFullById(int id) throws Exception;

    List<SmartWatch> getFullAll() throws Exception;

    void linkHealthFunction(int smartwatchId, int healthFunctionId) throws SQLException;
    void linkCompatibleOs(int smartwatchId, int operatingSystemId) throws SQLException;
}
