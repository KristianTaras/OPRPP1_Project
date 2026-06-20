package hr.algebra.model.repositories.interfaces;

import hr.algebra.model.entities.OperatingSystem;

import java.util.Set;

public interface OperatingSystemRepository extends Repository<OperatingSystem> {
    Set<OperatingSystem> getBySmartWatchId(int id) throws Exception;
}
