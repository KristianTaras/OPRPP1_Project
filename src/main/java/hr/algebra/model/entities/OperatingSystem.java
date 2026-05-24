package hr.algebra.model.entities;

import hr.algebra.model.interfaces.Transient;

import java.util.Set;

public class OperatingSystem extends BaseEntity
{
    private String name;
    private String version;
    private String developer;

    @Transient
    private Set<SmartWatchOperatingSystem> smartWatchOperatingSystems;

    public OperatingSystem(int id, String name, String version, String developer){
        super(id);
        this.name = name;
        this.version = version;
        this.developer = developer;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getDeveloper() { return developer; }
    public void setDeveloper(String developer) { this.developer = developer; }


    public Set<SmartWatchOperatingSystem> getSmartWatchOperatingSystems() {
        return smartWatchOperatingSystems;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof OperatingSystem that && super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
