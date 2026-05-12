package hr.algebra.model.entities;

public class Category extends BaseEntity{

    private String name;

    public Category(int id){
        super(id);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
