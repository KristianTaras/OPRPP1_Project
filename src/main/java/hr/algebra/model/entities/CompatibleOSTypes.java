package hr.algebra.model.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@XmlRootElement(name = "CompatibleOsTypes")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompatibleOSTypes extends BaseEntity {

    @XmlElement(name = "SmartWatchId")
    private int smartWatchId;
    @XmlElement(name = "OperatingSystemId")
    private int operatingSystemId;

    public CompatibleOSTypes(int id, int smartWatchId, int operatingSystemId){
        super(id);
        this.smartWatchId = smartWatchId;
        this.operatingSystemId = operatingSystemId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompatibleOSTypes that)) return false;
        if (!super.equals(o)) return false;
        return smartWatchId == that.smartWatchId && operatingSystemId == that.operatingSystemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), smartWatchId, operatingSystemId);
    }
}
