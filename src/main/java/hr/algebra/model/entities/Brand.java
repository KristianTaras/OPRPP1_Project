package hr.algebra.model.entities;

import java.util.Objects;

public class Brand extends BaseEntity {

    private String name; //Apple, Samsung
    private String country;
    private String description;

    public Brand(int id, String name, String country, String description ){
        super(id);
        this.name = name;
        this.country = country;
        this.description = description;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brand)) return false;
        return getId() == ((Brand) o).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
