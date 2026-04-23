
package HotelRooms;
import java.util.ArrayList;


public class Room {
    private int roomId;
    private String roomNumber;
    private RoomType roomtype;
    private boolean isAvailable;
    private int floor;
    private boolean smokingAllowed;
    private ArrayList<Amenity> amenities;

    public  Room(int roomId, String roomNumber, RoomType type, int floor,boolean isAvailable,boolean smokingAllowed) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomtype = type;
        this.floor = floor;
        this.isAvailable = isAvailable;
        this.smokingAllowed=smokingAllowed;


    }

    public boolean isSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
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

    public RoomType getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(RoomType roomtype) {
        this.roomtype = roomtype;
    }

    public boolean isAvailable() {
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
        return roomtype.getBasePrice() * nights;
    }
}
