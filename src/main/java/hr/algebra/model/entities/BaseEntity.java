package hr.algebra.model.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BaseEntity")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseEntity {

    protected BaseEntity() {} //protected better than public/private for only jakarta access

    @XmlElement(name = "Id")
    private int id;

    protected BaseEntity(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){ this.id = id; }

    @Override
    public boolean equals(Object o) {
        return o instanceof BaseEntity entity && id == entity.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
