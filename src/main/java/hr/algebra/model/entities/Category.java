package hr.algebra.model.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Category")
@XmlAccessorType(XmlAccessType.FIELD)
public class Category extends BaseEntity{

    public Category() { super(0); }

    public Category(int id){
        super(id);
    }

    @XmlElement(name = "Name")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
