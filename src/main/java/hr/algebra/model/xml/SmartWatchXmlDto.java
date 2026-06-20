package hr.algebra.model.xml;


import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "SmartWatch")
@XmlAccessorType(XmlAccessType.FIELD)
public class SmartWatchXmlDto {

    @XmlElement(name = "Id")
    private int id;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "YearOfMaking")
    private int yearOfMaking;

    @XmlElement(name = "ScreenSize")
    private Double screenSize;

    @XmlElement(name = "BatteryLife")
    private int batteryLife;

    @XmlElement(name = "IpRating")
    private String ipRating;

    @XmlElement(name = "Price")
    private double price;

    @XmlElement(name = "Brand")
    private String brand;

    @XmlElement(name = "BrandCountry")
    private String brandCountry;

    @XmlElement(name = "BrandDescription")
    private String brandDescription;

    @XmlElement(name = "Category")
    private String category;

    @XmlElement(name = "OperatingSystem")
    private String operatingSystem;

    @XmlElement(name = "OsVersion")
    private String osVersion;

    @XmlElement(name = "OsDeveloper")
    private String osDeveloper;

    @XmlElementWrapper(name = "HealthFunctions")
    @XmlElement(name = "HealthFunction")
    private List<String> healthFunctions;

    @XmlElementWrapper(name = "CompatibleOsTypes")
    @XmlElement(name = "OsType")
    private List<String> osTypes;

    // No arguments constructor - a must for jakarta
    public SmartWatchXmlDto() {}

    // Get / Set
    public int getId()                        { return id; }
    public void setId(int id)                 { this.id = id; }

    public String getName()                   { return name; }
    public void setName(String name)          { this.name = name; }

    public void setYearOfMaking(int y)        { this.yearOfMaking = y; }

    public void setScreenSize(Double s)       { this.screenSize = s; }

    public void setBatteryLife(int b)         { this.batteryLife = b; }

    public void setIpRating(String ip)        { this.ipRating = ip; }

    public double getPrice()                  { return price; }
    public void setPrice(double price)        { this.price = price; }

    public String getBrand()                  { return brand; }
    public void setBrand(String brand)        { this.brand = brand; }

    public void setBrandCountry(String c)     { this.brandCountry = c; }

    public void setBrandDescription(String d) { this.brandDescription = d; }

    public String getCategory()               { return category; }
    public void setCategory(String category)  { this.category = category; }

    public void setOperatingSystem(String os) { this.operatingSystem = os; }

    public void setOsVersion(String v)        { this.osVersion = v; }

    public void setOsDeveloper(String d)      { this.osDeveloper = d; }

    public void setHealthFunctions(List<String> hf)   { this.healthFunctions = hf; }

    public void setOsTypes(List<String> osTypes)      { this.osTypes = osTypes; }
}
