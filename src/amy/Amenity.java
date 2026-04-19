
package amy;

public class Amenity {
    private int amenityId;
    private String name;
    private String description;
    private double cost;
    public Amenity(int amenityId, String name, String description, double cost) {
        this.amenityId = amenityId;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public void setAmenityId(int amenityId) {
        this.amenityId = amenityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getAmenityId() {
        return amenityId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }


}

