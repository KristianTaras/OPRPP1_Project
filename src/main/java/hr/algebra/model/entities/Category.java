package hr.algebra.model.entities;

import java.util.Objects;
import java.util.Set;


public class Category extends BaseEntity{

    private String name;
    private Set<SmartWatch> watches;

    public Category(int id, String name){
        super(id); this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<SmartWatch> getWatches() {
        return watches;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Category)) return false;
        return getId() == ((Category) o).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
