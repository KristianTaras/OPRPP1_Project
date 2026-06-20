package hr.algebra.model.entities;


import hr.algebra.model.interfaces.Column;

import java.util.Objects;

public class SmartWatchHealthFunction extends BaseEntity {

    public SmartWatchHealthFunction() { super(0); }
    public SmartWatchHealthFunction(int id) { super(id); }

    @Column(name = "smart_watch_id")
    private int smartWatchId;
    @Column(name = "health_function_id")
    private int healthFunctionId;

    public SmartWatchHealthFunction(int id, int smartWatchId, int healthFunctionId) {
        super(id);
        this.smartWatchId = smartWatchId;
        this.healthFunctionId = healthFunctionId;
    }

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
