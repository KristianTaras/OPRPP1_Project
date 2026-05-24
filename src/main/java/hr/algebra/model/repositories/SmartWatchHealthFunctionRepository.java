package hr.algebra.model.repositories;

import hr.algebra.model.entities.SmartWatchHealthFunction;

import java.sql.SQLException;
import java.util.Set;

public interface SmartWatchHealthFunctionRepository extends Repository<SmartWatchHealthFunction> {

    Set<SmartWatchHealthFunction> getBySmartWatchId(int smartWatchId) throws SQLException;
}
