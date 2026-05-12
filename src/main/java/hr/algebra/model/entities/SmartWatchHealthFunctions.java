package hr.algebra.model.entities;

public class SmartWatchHealthFunctions extends BaseEntity {

    public SmartWatchHealthFunctions(int id) { super(id); }

    private int smartWatchId;
    private int healthFunctionId;

    public int getSmartWatchId() {
        return smartWatchId;
    }

    public void setSmartWatchId(int smartWatchId) {
        this.smartWatchId = smartWatchId;
    }

    public int getHealthFunctionId() {
        return healthFunctionId;
    }

    public void setHealthFunctionId(int healthFunctionId) {
        this.healthFunctionId = healthFunctionId;
    }
}
