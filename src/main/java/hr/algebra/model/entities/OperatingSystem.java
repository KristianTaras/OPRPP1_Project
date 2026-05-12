package hr.algebra.model.entities;

public class OperatingSystem extends BaseEntity
{

    public OperatingSystem(int id){
        super(id);
    }

    private String name;
    private String version;
    private String developer;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getDeveloper() { return developer; }
    public void setDeveloper(String developer) { this.developer = developer; }
}
