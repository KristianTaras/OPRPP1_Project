package hr.algebra.model.entities;

public class Brand extends BaseEntity {


    public Brand(int id){
        super(id);
    }

    private String name; //Apple, Samsung
    private String country;
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
