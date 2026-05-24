package hr.algebra.model.entities;

import hr.algebra.model.interfaces.Column;
import hr.algebra.model.interfaces.Transient;
import java.util.HashSet;
import java.util.Set;

public class SmartWatch extends BaseEntity {

    private final String name;
    @Column(name = "year_of_making")
    private final int yearOfMaking;
    @Column(name = "screen_size")
    private final Double screenSize;
    @Column(name = "battery_life")
    private final int batteryLife;
    @Column(name = "ip_rating")
    private final String ipRating; //Screen protection
    @Transient
    private Set<OperatingSystem> osTypes = new HashSet<>();
    private final double price;
    @Column(name = "image_path")
    private final String imagePath; //imageUrl
    @Column(name = "brand_id")
    private int brandId;
    @Column(name = "category_id")
    private int categoryId;
    @Column(name = "operating_system_id")
    private int operatingSystemId;
    @Transient
    private final Brand brand;
    @Transient
    private Set<SmartWatchHealthFunction> smartWatchHealthFunction = new HashSet<>();
    @Transient
    private final Category category;
    @Transient
    private final OperatingSystem operatingSystem;
    @Transient
    private Set<HealthFunction> healthFunctions = new HashSet<>();

    public SmartWatch(int id, String name, int yearOfMaking, double screenSize, int batteryLife, String ipRating,
                      double price, String imagePath, Brand brand, Category category,
                      OperatingSystem operatingSystem){
        super(id);
        this.name = name;
        this.yearOfMaking = yearOfMaking;
        this.screenSize = screenSize;
        this.batteryLife = batteryLife;
        this.ipRating = ipRating;
        this.price = price;
        this.imagePath = imagePath;
        this.brand = brand;
        this.category = category;
        this.operatingSystem = operatingSystem;

        this.brandId = brand != null ? brand.getId() : 0;
        this.categoryId = category != null ? category.getId() : 0;
        this.operatingSystemId = operatingSystem != null ? operatingSystem.getId() : 0;
    }

    //get
    //set

    public String getName() {
        return name;
    }

    public int getYearOfMaking() {
        return yearOfMaking;
    }

    public Double getScreenSize() {
        return screenSize;
    }

    public int getBatteryLife() {
        return batteryLife;
    }

    public String getIpRating() {
        return ipRating;
    }

    public Set<OperatingSystem> getOsTypes() {
        return osTypes;
    }

    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Brand getBrand() {
        return brand;
    }

    public Set<SmartWatchHealthFunction> getSmartWatchHealthFunctions() {
        return smartWatchHealthFunction;
    }

    public Category getCategory() {
        return category;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOsTypes(Set<OperatingSystem> osTypes) {
        this.osTypes = osTypes;
    }

    public void setSmartWatchHealthFunction(Set<SmartWatchHealthFunction> healthFunctions) {
        this.smartWatchHealthFunction = healthFunctions;
    }

    public Set<HealthFunction> getHealthFunctions() {
        return healthFunctions;
    }

    public void setHealthFunctions(Set<HealthFunction> healthFunctions) {
        this.healthFunctions = healthFunctions;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SmartWatch that && super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
