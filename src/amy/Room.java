
package amy;
import model.*;
import model.enums.*;
import java.util.ArrayList;


public class Room {
    private int roomId;
    private String roomNumber;
    private RoomType type;
    private boolean isAvailable;
    private int floor;
    private ArrayList<Amenity> amenities;

    public  Room(int roomId, String roomNumber, RoomType type, int floor,boolean isAvailable) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.type = type;
        this.floor = floor;
        this.isAvailable = isAvailable;


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



    public int getRoomId() {
        return roomId;
    }

    public ArrayList<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<Amenity> amenities) {
        this.amenities = amenities;
    }



    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }





    public boolean isIsAvailable() {


        return isAvailable;
    }
    public void addAmenity(Amenity aminity){
        if(amenities !=null && !amenities.isEmpty()){
            this.getAmenities().add(aminity) ;
        }else {
            amenities=new ArrayList<Amenity>();
            amenities.add(aminity);

        }


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
