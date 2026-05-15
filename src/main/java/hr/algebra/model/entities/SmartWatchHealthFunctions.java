package hr.algebra.model.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SmartWatchHealthFunctions")
@XmlAccessorType(XmlAccessType.FIELD)
public class SmartWatchHealthFunctions extends BaseEntity {

    public SmartWatchHealthFunctions() { super(0); }
    public SmartWatchHealthFunctions(int id) { super(id); }

    @XmlElement(name = "SmartWatchId")
    private int smartWatchId;
    @XmlElement(name = "HealthFunctionId")
    private int healthFunctionId;

    public int getSmartWatchId() { return smartWatchId; }

    public void setSmartWatchId(int smartWatchId) { this.smartWatchId = smartWatchId; }

    public int getHealthFunctionId() { return healthFunctionId; }

    public void setHealthFunctionId(int healthFunctionId) { this.healthFunctionId = healthFunctionId; }
}
