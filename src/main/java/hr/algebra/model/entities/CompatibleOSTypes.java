package hr.algebra.model.entities;

public class CompatibleOSTypes extends BaseEntity {

    public CompatibleOSTypes(int id) { super(id); }

    private int smartWatchId;

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

    private int operatingSystemId;
}
