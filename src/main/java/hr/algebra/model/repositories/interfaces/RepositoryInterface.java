package hr.algebra.model.repositories.interfaces;

import hr.algebra.model.entities.BaseEntity;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RepositoryInterface<T extends BaseEntity> {

    List<T> getAll();

    Optional<T> getBy(int id);

    void save(T entity) throws Exception;

    void deleteById(int id) throws Exception;

    boolean exists(T entity, int id) throws SQLException;




}
