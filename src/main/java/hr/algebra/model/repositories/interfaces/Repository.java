package hr.algebra.model.repositories.interfaces;

import hr.algebra.model.entities.BaseEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T extends BaseEntity> {

    List<T> getAll();

    Optional<T> getBy(int id) throws SQLException;

    Optional<T> getByName(String name);

    void save(T entity) throws Exception;

    void deleteById(int id) throws Exception;

    boolean exists(T entity, int id) throws SQLException;




}
