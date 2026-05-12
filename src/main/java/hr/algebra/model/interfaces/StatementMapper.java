package hr.algebra.model.interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementMapper<T> {
    void map(PreparedStatement stmt, T entity) throws SQLException;
}
