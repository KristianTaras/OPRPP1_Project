package hr.algebra.model.repositories;

import hr.algebra.model.entities.HealthFunction;

import java.util.Set;

public interface HealthFunctionRepository extends Repository<HealthFunction>{

    Set<HealthFunction> getBySmartWatchId(int id) throws Exception;
}
