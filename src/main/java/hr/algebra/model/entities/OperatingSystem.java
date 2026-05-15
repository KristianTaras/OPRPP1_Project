package hr.algebra.model.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OperatingSystem")
@XmlAccessorType(XmlAccessType.FIELD)
public class OperatingSystem extends BaseEntity
{
    public OperatingSystem() { super(0); }

    public OperatingSystem(int id){
        super(id);
    }

    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Version")
    private String version;
    @XmlElement(name = "Developer")
    private String developer;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getDeveloper() { return developer; }
    public void setDeveloper(String developer) { this.developer = developer; }
}
