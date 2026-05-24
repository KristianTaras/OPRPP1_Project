package hr.algebra.model.entities;

import hr.algebra.model.interfaces.Column;

import java.util.Objects;

public class SmartWatchOperatingSystem extends BaseEntity {

    @Column(name = "smart_watch_id")
    private int smartWatchId;
    @Column(name = "operating_system_id")
    private int operatingSystemId;

    public SmartWatchOperatingSystem(int id, int smartWatchId, int operatingSystemId){
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
        if (!(o instanceof SmartWatchOperatingSystem that)) return false;
        if (!super.equals(o)) return false;
        return smartWatchId == that.smartWatchId && operatingSystemId == that.operatingSystemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), smartWatchId, operatingSystemId);
    }
}
