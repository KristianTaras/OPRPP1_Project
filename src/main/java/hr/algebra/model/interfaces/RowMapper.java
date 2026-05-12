package hr.algebra.model.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {
    T map(ResultSet rs) throws SQLException;
    //convert a database row into an object through a provided mapping function
    //convert ResultSet row into a Java Object T
}
