package hr.algebra.model.entities;


public abstract class BaseEntity {

    //Diamond ID, so every class can implement its own id generator
    //@Ignore //It will ignore this property when looking for properties to send to database
    private int id;

    protected BaseEntity(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){ this.id = id; }

    @Override
    public boolean equals(Object o) {
        return o instanceof BaseEntity entity && id == entity.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
