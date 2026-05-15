package hr.algebra.model.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HealthFunction")
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthFunction extends BaseEntity {

    public HealthFunction() { super(0); }

    public HealthFunction(int id) {
        super(id);
    }

    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Description")
    private String description;

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
