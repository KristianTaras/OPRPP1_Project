package hr.algebra.model.entities;

import hr.algebra.model.interfaces.Transient;
import java.util.Set;

public class HealthFunction extends BaseEntity {

    private String name;
    private String description;

    @Transient
    private Set<SmartWatchHealthFunction> smartWatchHealthFunctions;

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

    public Set<SmartWatchHealthFunction> getSmartWatchHealthFunctions() {
        return smartWatchHealthFunctions;
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
