package HotelRooms;


public class Amenity {
    //  Auto-increment counter
    private static int nextId = 1;
    private int amenityId;
    private String name;
    private String description;
    private double cost;
    public Amenity(int amenityId, String name, String description, double cost) {
        //exception handling
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Amenity name cannot be empty.");
        if (cost < 0)
            throw new IllegalArgumentException("Daily cost cannot be negative.");
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
        this.description = description == null ? "" : description.trim();
    }

    public void setCost(double Cost) {
        if (Cost < 0)
            throw new IllegalArgumentException("Daily cost cannot be negative.");
        this.cost = Cost;
    }
    /** Reset the ID counter — used only during testing / database reload. */
    public static void resetIdCounter() { nextId = 1; }

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

