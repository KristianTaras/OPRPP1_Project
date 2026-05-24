package hr.algebra.model.entities;

import java.util.Set;

public class OperatingSystem extends BaseEntity
{
    private String name;
    private String version;
    private String developer;
    private Set<CompatibleOSTypes> compatibleOSTypes;

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


    public Set<CompatibleOSTypes> getCompatibleOSTypes() {
        return compatibleOSTypes;
    }

}
