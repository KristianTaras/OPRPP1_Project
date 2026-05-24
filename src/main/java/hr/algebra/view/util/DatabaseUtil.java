package hr.algebra.view.util;

import hr.algebra.model.json.SmartWatchSeeder;
import hr.algebra.security.passwordHashCheck.BCryptService;
import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DatabaseUtil {

    private static final Properties properties = new Properties();
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    private static final String filePath = "/sql/DBData.sql";


    static{
        try(InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("db.properties")){
            if(input == null) throw new RuntimeException("Unable to find db.properties");
            properties.load(input);

            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");

            try(Connection connection = newConnection()){
                initSchema(connection);
                insertAdmin(connection, filePath);
            }

        }catch(Exception ex){
            throw new RuntimeException("Failed to load database configuration", ex);
        }
    }



    private static Connection newConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }


    private DatabaseUtil(){}

    public static Connection getConnection() throws SQLException { return newConnection(); }

    private static void initSchema(Connection connection) throws IOException {

        String ddl = new String(
                DatabaseUtil.class.getResourceAsStream(
                        "/sql/DBScheme.sql"
                ).readAllBytes()
        );

        try(Statement stmt = connection.createStatement()){
            for(String sql : ddl.split(";")){
                String sqlTrimmed = sql.trim();
                if(!sqlTrimmed.isEmpty()){
                    stmt.execute(sqlTrimmed);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Database has been created");

        SmartWatchSeeder.seedIfEmpty();

    }

    private static void insertAdmin(Connection connection, String filePath) throws Exception {

        InputStream is = DatabaseUtil.class.getResourceAsStream(filePath);
        if(is == null){
            System.out.println("DBData.sql not found, skipping insert");
            throw new RuntimeException();
        }


        List<String> lines = new BufferedReader(new InputStreamReader(is)).lines().toList();

        for(String line : lines){
            if(line.startsWith("--")){
                String data = line.substring(2).trim();
                String[] parts = data.split(",");

                String rawPassword = parts[5];
                String hashedPassword = BCryptService.hashPassword(rawPassword);

                parts[5] = hashedPassword;

                insertUserIntoDB(connection,parts);


            }
        }



    }

    private static void insertUserIntoDB(Connection connection,String[] parts) {
        String sql = """
                       IF NOT EXISTS(SELECT 1 FROM [User] WHERE username = 'AdminView')
                       BEGIN
                       
                       INSERT INTO [User] (first_name, last_name, email, phone_number, username, password_hash, role)
                       VALUES(?,?,?,?,?,?,?)
                       
                       END
                       """;

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, parts[0]);
            ps.setString(2, parts[1]);
            ps.setString(3, parts[2]);
            ps.setString(4, parts[3]);
            ps.setString(5, parts[4]);
            ps.setString(6, parts[5]);
            ps.setString(7, parts[6]);

            ps.executeUpdate();
            System.out.println("Admin successfully added");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
