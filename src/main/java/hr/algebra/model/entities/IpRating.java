package hr.algebra.model.entities;

public class IpRating extends BaseEntity {

    private String value;

    public IpRating(int id){
        super(id);
    }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }
}
