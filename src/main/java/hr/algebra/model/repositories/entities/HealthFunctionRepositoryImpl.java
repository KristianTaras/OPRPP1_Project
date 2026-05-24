package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.mapper.HealthFunctionMapper;
import hr.algebra.model.repositories.HealthFunctionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class HealthFunctionRepositoryImpl extends RepositoryImpl<HealthFunction> implements HealthFunctionRepository {

    private final Connection connection;

    public HealthFunctionRepositoryImpl(Connection connection, String table, RowMapper<HealthFunction> mapper) {
        super(connection, table, mapper);
        this.connection = connection;
    }

    @Override
    public Set<HealthFunction> getBySmartWatchId(int id) throws Exception {
        String sql = """
            SELECT hf.id, hf.name, hf.description
            FROM HealthFunction hf
            INNER JOIN SmartWatchHealthFunction swhf ON hf.id = swhf.health_function_id
            WHERE swhf.smart_watch_id = ?
            """;

        Set<HealthFunction> healthFunctions = new HashSet<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                HealthFunctionMapper mapper = new HealthFunctionMapper();
                while (rs.next()) {
                    healthFunctions.add(mapper.map(rs));
                }
            }
        } catch (Exception ex) {
            throw new Exception(STR."getBySmartWatchId failed: \{ex.getMessage()}");
        }

        return healthFunctions;
    }
}
