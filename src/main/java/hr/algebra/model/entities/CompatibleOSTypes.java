package hr.algebra.model.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CompatibleOsTypes")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompatibleOSTypes extends BaseEntity {

    public CompatibleOSTypes() { super(0); }

    public CompatibleOSTypes(int id) { super(id); }

    @XmlElement(name = "SmartWatchId")
    private int smartWatchId;
    @XmlElement(name = "OperatingSystemId")
    private int operatingSystemId;

    public int getSmartWatchId() {
        return smartWatchId;
    }
    public void setSmartWatchId(int smartWatchId) {
        this.smartWatchId = smartWatchId;
    }

    public int getOperatingSystemId() {
        return operatingSystemId;
    }
    public void setOperatingSystemId(int operatingSystemId) {
        this.operatingSystemId = operatingSystemId;
    }


}
