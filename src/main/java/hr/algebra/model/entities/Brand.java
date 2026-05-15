package hr.algebra.model.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Brand")
@XmlAccessorType(XmlAccessType.FIELD)
public class Brand extends BaseEntity {

    public Brand() { super(0); }

    public Brand(int id){
        super(id);
    }

    @XmlElement(name = "Name")
    private String name; //Apple, Samsung
    @XmlElement(name = "Country")
    private String country;
    @XmlElement(name = "Description")
    private String description;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
