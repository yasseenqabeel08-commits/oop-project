
package HotelRooms;
import model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {


    //data fields
        private static int nextId=1;
        private final int roomId;
        private String roomNumber;
        private RoomType type;
        private double pricePerNight;
        private boolean isAvailable;
        private int floor;
        private boolean smokingAllowed;
        private ArrayList<Amenity> amenities =new ArrayList<>();
        //constructors
        public Room(int roomId, String roomNumber, RoomType type, double pricePerNight ,int floor,boolean isAvailable,boolean smokingAllowed) {
            if (roomNumber == null || roomNumber.isBlank())
                throw new IllegalArgumentException("Room number cannot be empty.");
            if (type == null)
                throw new IllegalArgumentException("Room must have a type.");
            if (pricePerNight <= 0)
                throw new IllegalArgumentException("Price per night must be > 0.");
            this.roomId = nextId++;
            this.roomNumber = roomNumber.trim();
            this.type = type;
            this.floor = floor;
            this.isAvailable = true;
            this.smokingAllowed=smokingAllowed;
            this.pricePerNight=pricePerNight;
        }
        // method to add amenity
        public void addAmenity(Amenity amenity) {
            if (amenity != null && !amenities.contains(amenity))
                amenities.add(amenity);
        }
        //method to remove amenity
        public void removeAmenity(Amenity amenity) {
            amenities.remove(amenity);
        }


        //setter
        public void setRoomNumber(String roomNumber) {
            if (roomNumber == null || roomNumber.isBlank())
                throw new IllegalArgumentException("Room number cannot be empty.");
            this.roomNumber = roomNumber.trim();
        }

        public void setRoomType(RoomType type) {
            if (type == null) throw new IllegalArgumentException("Room type cannot be null.");
            this.type = type;
        }
        public void setPricePerNight(double pricePerNight) {
            if (pricePerNight <= 0)
                throw new IllegalArgumentException("Price per night must be > 0.");
            this.pricePerNight = pricePerNight;
        }

        public void setAvailable(boolean available)       { this.isAvailable    = available;    }
        public void setFloor(int floor)                   { this.floor          = floor;        }
        public void setSmokingAllowed(boolean smoking)    { this.smokingAllowed = smoking;      }
        //getter
        public ArrayList<Amenity> getAmenities() {
            return amenities;
        }
        public int getRoomId() {
            return roomId;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

//        public ArrayList getAmenities() {
//            return amenities;
//        }


        public RoomType getRoomtype() {
            return type;
        }

        public double getPricePerNight() {
            return pricePerNight;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public int getFloor() {
            return floor;
        }

        public boolean isSmokingAllowed() {
            return smokingAllowed;
        }

        /**
         * Cost calculation
         * Calculate the total stay cost for the given number of nights.
         * Includes the nightly room rate plus the daily cost of each amenity.
         *
         * @param nights number of nights (must be > 0)
         * @return total cost in EGP
         */
        public double calculateCost(long nights) {
            if (nights <= 0)
                throw new IllegalArgumentException("Number of nights must be positive.");

            double amenityCost = amenities.stream()
                    .mapToDouble(Amenity::getCost)
                    .sum();
            return (pricePerNight + amenityCost) * nights;
        }
        public static void resetIdCounter() { nextId = 1; }

        @Override
        public String toString() {
            String amenityNames="";
            if(!amenities.isEmpty()){
                for(Amenity a:amenities){
                    amenityNames = amenityNames + a.getName() + ",";

                }
            }else {
                amenityNames="None";
            }

            return String.format(
                    "[Room #%d] %s | %-10s | Floor %d | EGP %.2f/night | %-12s | Amenities: %s",
                    roomId, roomNumber, type.getTypeName(), floor,
                    pricePerNight, isAvailable ? "Available" : "Occupied", amenityNames);
        }

    }
