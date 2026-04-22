
package amy;
import java.util.ArrayList;
import  java.util.Scanner;



public class Room {
    private int roomId;
    private String roomNumber;
    private RoomType type;
    private boolean isAvailable;
    private int floor;
    private ArrayList<Amenity> amenities;

    public Room(int roomId, String roomNumber, RoomType type, int floor) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.type = type;
        this.floor = floor;
        this.isAvailable = true;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setType(RoomType type) {
        this.type = type;
    }


    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setAmenities(ArrayList amenities) {
        this.amenities = amenities;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public ArrayList getAmenities() {
        return amenities;
    }


    public boolean isIsAvailable() {
        return isAvailable;
    }

    public int getFloor() {
        return floor;
    }

    public double calculateCost(int nights) {
        if (nights <= 0) {
            return 0;
        }
        return type.getBasePrice() * nights;
    }

}
