package HotelRooms;


public class RoomType {
    private int typeId;
    private static int nextId = 1;
    private String typeName;
    private String description;
    private double basePrice;
    public RoomType(int typeId, String typeName, String description, double basePrice) {
        if (typeName == null || typeName.isBlank())
            throw new IllegalArgumentException("Room type name cannot be empty.");
        if (basePrice <= 0)
            throw new IllegalArgumentException("Base price must be greater than zero.");
        this.typeId = nextId++;
        this.typeId=typeId;
        this.typeName = typeName;
        this.description = description;
        this.basePrice = basePrice;
    }

    public String getTypeName() {

        return typeName;
    }

    public void setTypeName(String typeName) {
        if (typeName == null || typeName.isBlank())
            throw new IllegalArgumentException("Room type name cannot be empty.");
        this.typeName = typeName.trim();
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description.trim();
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice <= 0)
            throw new IllegalArgumentException("Base price must be greater than zero.");
        this.basePrice = basePrice;

    }
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    @Override
    public String toString() {
        return String.format("[RoomType #%d] %-12s | EGP %.2f/night | %s",
                typeId, typeName, basePrice, description);
    }
}

