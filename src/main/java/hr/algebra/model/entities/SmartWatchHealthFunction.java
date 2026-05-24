package hr.algebra.model.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@XmlRootElement(name = "SmartWatchHealthFunctions")
@XmlAccessorType(XmlAccessType.FIELD)
public class SmartWatchHealthFunction extends BaseEntity {

    public SmartWatchHealthFunction() { super(0); }
    public SmartWatchHealthFunction(int id) { super(id); }

    @XmlElement(name = "SmartWatchId")
    private int smartWatchId;
    @XmlElement(name = "HealthFunctionId")
    private int healthFunctionId;

    public SmartWatchHealthFunction(int id, int smartWatchId, int healthFunctionId) {
        super(id);
        this.smartWatchId = smartWatchId;
        this.healthFunctionId = healthFunctionId;
    }

    public int getSmartWatchId() { return smartWatchId; }

    public void setSmartWatchId(int smartWatchId) { this.smartWatchId = smartWatchId; }

    public int getHealthFunctionId() { return healthFunctionId; }

    public void setHealthFunctionId(int healthFunctionId) { this.healthFunctionId = healthFunctionId; }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SmartWatchHealthFunction that)) return false;
        if (!super.equals(o)) return false;
        return smartWatchId == that.smartWatchId && healthFunctionId == that.healthFunctionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), smartWatchId, healthFunctionId);
    }
}
