package hr.algebra.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties properties = new Properties();
    //store key value pairs from db.properties into memory
    //Real apps often use ClassLoader.getResourceAsStream("db.properties");

    private DatabaseConfig(){}


    //static block runs once when the class is loaded
    static{
        try(FileInputStream file = new FileInputStream("db.properties")){
            properties.load(file);
        }catch (IOException ex){
            throw new RuntimeException("Failed to load database configuration");
            //Replace RuntimeException for a custom one
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
