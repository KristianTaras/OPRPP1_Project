package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.HealthFunction;
import hr.algebra.model.entities.OperatingSystem;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.mapper.HealthFunctionMapper;
import hr.algebra.model.mapper.OperatingSystemMapper;
import hr.algebra.model.repositories.OperatingSystemRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class OperatingSystemRepositoryImpl extends RepositoryImpl<OperatingSystem> implements OperatingSystemRepository {

    private final Connection connection;

    public OperatingSystemRepositoryImpl(Connection connection, String table, RowMapper<OperatingSystem> mapper) {
        super(connection, table, mapper);
        this.connection = connection;
    }

    @Override
    public Set<OperatingSystem> getBySmartWatchId(int id) throws Exception {
        String sql = """
            SELECT os.id, os.name, os.version, os.developer
            FROM OperatingSystem os
            INNER JOIN CompatibleOsTypes cot ON os.id = cot.operating_system_id
            WHERE cot.smart_watch_id = ?
            """;

        Set<OperatingSystem> operatingSystems = new HashSet<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                OperatingSystemMapper mapper = new OperatingSystemMapper();
                while (rs.next()) {
                    operatingSystems.add(mapper.map(rs));
                }
            }
        } catch (Exception ex) {
            throw new Exception(STR."getBySmartWatchId failed: \{ex.getMessage()}");
        }

        return operatingSystems;
    }
}
