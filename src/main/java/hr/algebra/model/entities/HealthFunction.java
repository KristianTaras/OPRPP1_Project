package hr.algebra.model.entities;

public class HealthFunction extends BaseEntity {

    private String name;
    private String description;

    public HealthFunction(int id, String name, String description){
        super(id);
        this.name = name;
        this.description = description;
    }

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

    @Override
    public boolean equals(Object o) {
        return o instanceof HealthFunction that && super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
