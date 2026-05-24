package hr.algebra.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SmartWatchDTO {

    public int id;
    public String name;
    public int yearOfMaking;
    public double screenSize;
    public int batteryLife;
    public String ipRating;
    public List<String> osTypes;
    public double price;
    public String imagePath;
    public String brand;
    public List<String> healthFunctions;
    public String category;
    public String operatingSystem;
}
