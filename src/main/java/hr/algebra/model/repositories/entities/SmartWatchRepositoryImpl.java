package hr.algebra.model.repositories.entities;

import hr.algebra.model.entities.*;
import hr.algebra.model.interfaces.RowMapper;
import hr.algebra.model.mapper.BrandMapper;
import hr.algebra.model.mapper.HealthFunctionMapper;
import hr.algebra.model.mapper.SmartWatchMapper;
import hr.algebra.model.repositories.HealthFunctionRepository;
import hr.algebra.model.repositories.OperatingSystemRepository;
import hr.algebra.model.repositories.SmartWatchHealthFunctionRepository;
import hr.algebra.model.repositories.SmartWatchRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SmartWatchRepositoryImpl extends RepositoryImpl<SmartWatch> implements SmartWatchRepository {

    private final Connection connection;
    private final SmartWatchHealthFunctionRepository smartWatchHealthFunctionRepository;
    private final OperatingSystemRepository operatingSystemRepository;
    private final HealthFunctionRepository healthFunctionRepository;

    public SmartWatchRepositoryImpl(Connection connection,
                                    String table,
                                    RowMapper<SmartWatch> mapper,
                                    SmartWatchHealthFunctionRepository smartWatchHealthFunctionRepository,
                                    OperatingSystemRepository operatingSystemRepository,
                                    HealthFunctionRepository healthFunctionRepository) {
        super(connection, table, mapper);
        this.connection = connection;
        this.smartWatchHealthFunctionRepository = smartWatchHealthFunctionRepository;
        this.operatingSystemRepository = operatingSystemRepository;
        this.healthFunctionRepository = healthFunctionRepository;
    }

    @Override
    public SmartWatch getFullById(int id) throws Exception {
        String sql = """
                SELECT sw.*,
                       b.name AS brand_name, b.country AS brand_country, b.description AS brand_desc,
                       c.name AS cat_name,
                       os.name AS os_name, os.version AS os_ver, os.developer AS os_dev
                FROM SmartWatch sw
                LEFT JOIN Brand b ON sw.brand_id = b.id
                LEFT JOIN Category c ON sw.category_id = c.id
                LEFT JOIN OperatingSystem os ON sw.operating_system_id = os.id
                WHERE sw.id = ?
                """;

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    SmartWatch watch = mapFull(rs);
                    enrich(watch);
                    return watch;
                }
            }
        }catch(Exception ex){
            throw new Exception("Get Full Smartwatch by id failed");

        }
        throw new Exception(STR."Smartwatch doesnt exist on id: \{id}");

    }

    @Override
    public List<SmartWatch> getFullAll() throws Exception {
        String sql = """
                SELECT sw.*,
                       b.name AS brand_name, b.country AS brand_country, b.description AS brand_desc,
                       c.name AS cat_name,
                       os.name AS os_name, os.version AS os_ver, os.developer AS os_dev
                FROM SmartWatch sw
                LEFT JOIN Brand b ON sw.brand_id = b.id
                LEFT JOIN Category c ON sw.category_id = c.id
                LEFT JOIN OperatingSystem os ON sw.operating_system_id = os.id
                """;

        List<SmartWatch> watches = new LinkedList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SmartWatch watch = mapFull(rs);
                enrich(watch);
                watches.add(watch);
            }
        }
        return watches;
    }

    private SmartWatch mapFull(ResultSet rs) throws Exception{
        Brand brand = new Brand(
                rs.getInt("brand_id"),
                rs.getString("brand_name"),
                rs.getString("brand_country"),
                rs.getString("brand_desc")
        );

        Category category = new Category(
                rs.getInt("category_id"),
                rs.getString("cat_name")
        );

        OperatingSystem os = new OperatingSystem(
                rs.getInt("operating_system_id"),
                rs.getString("os_name"),
                rs.getString("os_ver"),
                rs.getString("os_dev")
        );

        return new SmartWatch(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("year_of_making"),
                rs.getDouble("screen_size"),
                rs.getInt("battery_life"),
                rs.getString("ip_rating"),
                rs.getDouble("price"),
                rs.getString("image_path"),
                brand,
                category,
                os
        );
    }

    private void enrich(SmartWatch watch) throws Exception {
        watch.setSmartWatchHealthFunction(

                smartWatchHealthFunctionRepository.getBySmartWatchId(watch.getId())
        );
        watch.setOsTypes(
                operatingSystemRepository.getBySmartWatchId(watch.getId())
        );

        Set<HealthFunction> healthFunction = healthFunctionRepository.getBySmartWatchId(watch.getId());
        watch.setHealthFunctions(healthFunction);
    }


    @Override
    public void linkHealthFunction(int smartwatchId, int healthFunctionId) throws SQLException {
        String sql = "INSERT INTO SmartWatchHealthFunction (smart_watch_id, health_function_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, smartwatchId);
            ps.setInt(2, healthFunctionId);
            ps.executeUpdate();
        }
    }

    @Override
    public void linkOperatingSystem(int smartwatchId, int operatingSystemId) throws SQLException {
        String sql = "INSERT INTO SmartWatchOperatingSystem (smart_watch_id, operating_system_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, smartwatchId);
            ps.setInt(2, operatingSystemId);
            ps.executeUpdate();
        }
    }

}
