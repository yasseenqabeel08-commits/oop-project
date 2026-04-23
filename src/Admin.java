import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import amy.Amenity;
import amy.Invoice;
import amy.Room;
import amy.RoomType;
import model.enums.*;

public class Admin extends Staff implements Manageable {
    public Admin(){
        super();
    }
    public Admin(String username, String password, LocalDate dateOfBirth, Gender gender, Role role, int workinghours){
        super(username, password, dateOfBirth, gender,role,workinghours);
    }
    public void addRoom(Room room){
        if (room == null) throw new IllegalArgumentException("Room cannot be null.");
        boolean exists = HotelDatabase.getInstance().getRooms().stream()
                .anyMatch(r -> r.getRoomNumber().equalsIgnoreCase(room.getRoomNumber()));
        if (exists)
            throw new IllegalStateException("Room number '" + room.getRoomNumber() + "' already exists.");
        HotelDatabase.getInstance().getRooms().add(room);
        System.out.println("[ADMIN] Room " + room.getRoomNumber() + " added.");
    }
    public void deleteRoom(int roomId){
        List<Room> rooms = HotelDatabase.getInstance().getRooms();
        boolean removed = rooms.removeIf(r -> r.getRoomId() == roomId);
        if (!removed)
            throw new IllegalArgumentException("Room #" + roomId + " not found.");
        System.out.println("[ADMIN] Room #" + roomId + " deleted.");
    }


    public void updateRoom(Room updatedRoom){
        if (updatedRoom == null) throw new IllegalArgumentException("Room cannot be null.");
        List<Room> rooms = HotelDatabase.getInstance().getRooms();
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomId() == updatedRoom.getRoomId()) {
                rooms.set(i, updatedRoom);
                System.out.println("[ADMIN] Room #" + updatedRoom.getRoomId() + " updated.");
                return;
            }
    }
        throw new IllegalArgumentException("Room #" + updatedRoom.getRoomId() + " not found.");
    }

    public void addRoomType(RoomType roomType) {
        if (roomType == null) throw new IllegalArgumentException("RoomType cannot be null.");
        HotelDatabase.getInstance().getRoomTypes().add(roomType);
        System.out.println("[ADMIN] RoomType '" + roomType.getTypeName() + "' added.");
    }

    public void updateRoomType(RoomType updatedType) {
        if (updatedType == null) throw new IllegalArgumentException("RoomType cannot be null.");
        List<RoomType> types = HotelDatabase.getInstance().getRoomTypes();
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getTypeId() == updatedType.getTypeId()) {
                types.set(i, updatedType);
                System.out.println("[ADMIN] RoomType #" + updatedType.getTypeId() + " updated.");
                return;
            }
        }
        throw new IllegalArgumentException("RoomType #" + updatedType.getTypeId() + " not found.");
    }

    public void deleteRoomType(int typeId) {
        boolean inUse = HotelDatabase.getInstance().getRooms().stream()
                .anyMatch(r -> r.getRoomType().getTypeId() == typeId);
        if (inUse)
            throw new IllegalStateException("Cannot delete RoomType #" + typeId
                    + " — it is currently assigned to one or more rooms.");
        boolean removed = HotelDatabase.getInstance().getRoomTypes()
                .removeIf(t -> t.getTypeId() == typeId);
        if (!removed)
            throw new IllegalArgumentException("RoomType #" + typeId + " not found.");
        System.out.println("[ADMIN] RoomType #" + typeId + " deleted.");
    }

    public void createNewAmenities(int id, String name, String desc, double cost){
        Amenity amenity = new Amenity(id, name, desc, cost);
        System.out.println("New amenity:"+amenity+"created");
    }

    public void addAmenity(Amenity amenity) {
        if (amenity == null) throw new IllegalArgumentException("Amenity cannot be null.");
        HotelDatabase.getInstance().getAmenities().add(amenity);
        System.out.println("[ADMIN] Amenity '" + amenity.getName() + "' added.");
    }
    public void removeAmenityFromRoom(Room room, Amenity amenity) {
        if (room.getAmenities() != null) {
            room.getAmenities().remove(amenity);
            System.out.println(amenity.getName() + " removed from Room " + room.getRoomNumber());
        }
    }
    public void updateAmenity(Amenity updatedAmenity) {
        if (updatedAmenity == null) throw new IllegalArgumentException("Amenity cannot be null.");
        List<Amenity> amenities = HotelDatabase.getInstance().getAmenities();
        for (int i = 0; i < amenities.size(); i++) {
            if (amenities.get(i).getAmenityId() == updatedAmenity.getAmenityId()) {
                amenities.set(i, updatedAmenity);
                System.out.println("[ADMIN] Amenity #" + updatedAmenity.getAmenityId() + " updated.");
                return;
            }
        }
        throw new IllegalArgumentException("Amenity #" + updatedAmenity.getAmenityId() + " not found.");
    }
    public void deleteAmenity(int amenityId) {
        boolean removed = HotelDatabase.getInstance().getAmenities()
                .removeIf(a -> a.getAmenityId() == amenityId);
        if (!removed)
            throw new IllegalArgumentException("Amenity #" + amenityId + " not found.");
        System.out.println("[ADMIN] Amenity #" + amenityId + " deleted.");
    }




    @Override
    public void create() {
        System.out.println("[ADMIN] Use addRoom(), addRoomType(), or addAmenity() to create entities.");
    }

    @Override
    public void update() {
        System.out.println("[ADMIN] Use updateRoom(), updateRoomType(), or updateAmenity() to update entities.");
    }

    @Override
    public void delete() {
        System.out.println("[ADMIN] Use deleteRoom(), deleteRoomType(), or deleteAmenity() to delete entities.");
    }

    @Override
    public Object findById(int id) {
        // Search rooms first, then room types, then amenities
        HotelDatabase db = HotelDatabase.getInstance();

        return db.getRooms().stream().filter(r -> r.getRoomId() == id).findFirst()
                .<Object>map(r -> r)
                .or(() -> db.getRoomTypes().stream().filter(t -> t.getTypeId() == id).findFirst().<Object>map(t -> t))
                .or(() -> db.getAmenities().stream().filter(a -> a.getAmenityId() == id).findFirst().<Object>map(a -> a))
                .orElse(null);
    }

    // ── View helpers ──────────────────────────────────────────────────────────

    public List<RoomType> viewAllRoomTypes() {
        return List.copyOf(HotelDatabase.getInstance().getRoomTypes());
    }

    public List<Amenity> viewAllAmenities() {
        return List.copyOf(HotelDatabase.getInstance().getAmenities());
    }

    public List<Invoice> viewAllInvoices() {
        return List.copyOf(HotelDatabase.getInstance().getInvoices());
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("[Admin] %-20s | %s | %dh/week", username, fullName, workingHours);
    }

}
