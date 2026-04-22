import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.ArrayList;

import amy.Amenity;
import model.enums.*;

   public class Admin extends Staff {
    public Admin(){
        super();
    }
    public Admin(String username, String password, LocalDate dateOfBirth, Gender gender, Role role, int workinghours){
        super(username, password, dateOfBirth, gender,role,workinghours);
    }
    public void addRoom(Room room){
        Room newRoom = new Room();
        System.out.println("New room added");
    }
public void deleteRoom(int id,Room room){
        if(id==room.getRoomNumber()){
            room.getRoom().remove(room);
        }
}


    public void updateRoom(Room room,double newprice, boolean availability){
        room.setPrice(newprice);
        room.setIsAvailable(availability);
        System.out.println("Room:"+room.getRoomNumber()+ "updated");
    }
    public void createNewAmenities(int id, String name, String desc, double cost){
        Amenity amenity = new Amenity(id, name, desc, cost);
        System.out.println("New amenity:"+amenity+"created");
    }
    public void addAmenityToRoom(Room room, Amenity amenity){
        if(room.getAmenities!=null){
            room.getAmenities().add(amenity);
            System.out.println("New amenity:"+amenity+"added to room:"+room.getRoomNumber());
        }
    }
    public void removeAmenityFromRoom(Room room, Amenity amenity) {
        if (room.getAmenities() != null) {
            room.getAmenities().remove(amenity);
            System.out.println(amenity.getName() + " removed from Room " + room.getRoomNumber());
        }
    }
    public void manageAmenities(Room room, Amenity amenity,String choice){
     if(choice.equals("Add")){
  addAmenityToRoom(room,amenity);
}else if(choice.equals("Remove")){
         removeAmenityFromRoom(room,amenity);
     }
    }
    public void manageRoomTypes(String type,double newPrice){
        if(type!=null){
           type.setBasePrice(newPrice);
            System.out.println("Success: The base price for " + type.getTypeName() +
                    " has been updated to " + newPrice + " EGP.");
        }
        else{
            System.out.println("Error: Room type not found.");
        }
    }
}
//mmmmmmm