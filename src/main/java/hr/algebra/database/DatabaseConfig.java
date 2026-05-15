package hr.algebra.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties properties = new Properties();

    private DatabaseConfig(){}
    //static block runs once when the class is loaded
    static{
        try(InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")){
            if(input == null) throw new RuntimeException("Unable to find db.properties");
            properties.load(input);
        }catch(IOException ex){
            throw new RuntimeException("Failed to load database configuration", ex);
        }
    }

    public static String getUrl(){
        return properties.getProperty("db.url");
    }
    public static String getUser(){
        return properties.getProperty("db.user");
    }
    public static String getPassword(){
        return properties.getProperty("db.password");
    }

}
