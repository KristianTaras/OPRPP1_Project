package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.SmartWatchHealthFunction;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.repositories.SmartWatchHealthFunctionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class SmartWatchHealthFunctionRepositoryImpl extends RepositoryImpl<SmartWatchHealthFunction> implements SmartWatchHealthFunctionRepository {

    private final Connection connection;
    private final RowMapper<SmartWatchHealthFunction> mapper;

    public SmartWatchHealthFunctionRepositoryImpl(Connection connection, String table, RowMapper<SmartWatchHealthFunction> mapper) {
        super(connection, table, mapper);

        this.connection = connection;
        this.mapper = mapper;
    }


    @Override
    public Set<SmartWatchHealthFunction> getBySmartWatchId(int smartWatchId) throws SQLException {
        String sql = """
                    SELECT id, smart_watch_id, health_function_id
                    FROM SmartWatchHealthFunction
                    WHERE smart_watch_id = ?
                """;

        Set<SmartWatchHealthFunction> result = new HashSet<>();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, smartWatchId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    result.add(mapper.map(rs));
                }
            }
        }

        return result;
    }
}
