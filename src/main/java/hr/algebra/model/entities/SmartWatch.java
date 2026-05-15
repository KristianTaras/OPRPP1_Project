package hr.algebra.model.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.Year;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "SmartWatch")
@XmlAccessorType(XmlAccessType.FIELD)
public class SmartWatch extends BaseEntity {

    //Dummy constructor - need empty constructor to enable jakarta
    public SmartWatch(){
        super(0);
    }

    public SmartWatch(int id, String brand, String model, double price) {
        super(id);
    }
    //Extend the constructor so it takes all the properties

    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "YearOfMaking")
    private int yearOfMaking;
    @XmlElement(name = "ScreenSize")
    private Double screenSize;
    @XmlElement(name = "BatteryLife")
    private int batteryLife;
    @XmlElement(name = "IpRating")
    private IpRating ipRating; //Screen protection
    @XmlElement(name = "CompatibleOperatingSystems")
    private List<CompatibleOSTypes> osTypes;
    @XmlElement(name = "Price")
    private double price;
    @XmlElement(name = "ImagePath")
    private String imagePath; //imageUrl
    @XmlElement(name = "Brand")
    private Brand brand;
    @XmlElement(name = "HealthFunction")
    private Set<HealthFunction> healthFunctions;
    @XmlElement(name = "Category")
    private Category category;
    @XmlElement(name = "OperatingSystem")
    private OperatingSystem operatingSystem;

    //get
    //set

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getYearOfMaking() { return yearOfMaking; }
    public void setYearOfMaking(int yearOfMaking) { this.yearOfMaking = yearOfMaking; }

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
