package hr.algebra.model.entities;

import java.time.Year;
import java.util.List;
import java.util.Set;


public class SmartWatch extends BaseEntity {

    public SmartWatch(int id, String brand, String model, double price) {
        super(id);
    }
    //Extend the constructor so it takes all the properties
    private String name;
    private Year yearOfMaking;
    private Double screenSize;
    private int batteryLife;
    private IpRating ipRating; //Screen protection
    private List<CompatibleOSTypes> osTypes;

    private double price;
    private String imagePath; //imageUrl

    private Brand brand;
    private Set<HealthFunction> healthFunctions;
    private Category category;
    private OperatingSystem operatingSystem;

    //get
    //set

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Year getYearOfMaking() { return yearOfMaking; }
    public void setYearOfMaking(Year yearOfMaking) { this.yearOfMaking = yearOfMaking; }

    public Double getScreenSize() { return screenSize; }
    public void setScreenSize(Double screenSize) { this.screenSize = screenSize; }

    public int getBatteryLife() { return batteryLife; }
    public void setBatteryLife(int batteryLife) { this.batteryLife = batteryLife; }

    public IpRating getIpRating() { return ipRating; }
    public void setIpRating(IpRating ipRating) { this.ipRating = ipRating; }

    public List<CompatibleOSTypes> getOsTypes() { return osTypes; }
    public void setOsTypes(List<CompatibleOSTypes> osTypes) { this.osTypes = osTypes; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public Brand getBrand() { return brand; }
    public void setBrand(Brand brand) { this.brand = brand; }

    public Set<HealthFunction> getHealthFunctions() { return healthFunctions; }
    public void setHealthFunctions(Set<HealthFunction> healthFunctions) { this.healthFunctions = healthFunctions; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public OperatingSystem getOperatingSystem() { return operatingSystem; }
    public void setOperatingSystem(OperatingSystem operatingSystem) { this.operatingSystem = operatingSystem; }

    //equals, hashcode, toString, Comparable if needed for anything other than id




}
