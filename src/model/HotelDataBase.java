package model;
import amy.*;
import model.enums.Gender;
import model.enums.PaymentMethod;
import model.enums.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelDataBase {
private static HotelDataBase instance;
private static List<Guest> guests = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();
    private static List<Reservation> reservations= new ArrayList<>();
    private static List<Invoice> Invoices = new ArrayList<>();
    private static List<Staff> staff = new ArrayList<>();
    private static List<Amenity> amenities  = new ArrayList<>();
    private static List<RoomType> roomTypes = new ArrayList<>();
    private HotelDataBase() {
        populateDummyData();
    }
    public static HotelDataBase getInstance(){
        if (instance==null) {
            instance = new HotelDataBase();
        }
        return instance;
        }

    public static List<Room> getRooms() {
        return rooms;
    }

    public static void setRooms(List<Room> rooms) {
        HotelDataBase.rooms = rooms;
    }

    public static List<Reservation> getReservations() {
        return reservations;
    }

    public static void setReservations(List<Reservation> reservations) {
        HotelDataBase.reservations = reservations;
    }

    public static List<Invoice> getInvoices() {
        return Invoices;
    }

    public static void setInvoices(List<Invoice> Invoices) {
        HotelDataBase.Invoices = Invoices;
    }

    public static List<Guest> getGuests() {
        return guests;
    }

    public static void setGuests(List<Guest> guests) {
        HotelDataBase.guests = guests;
    }

    public static List<Staff> getStaff() {
        return staff;
    }

    public static void setStaff(List<Staff> staff) {
        HotelDataBase.staff = staff;
    }

    public static List<Amenity> getAmenities() {
        return amenities;
    }

    public static void setAmenities(List<Amenity> amenities) {
        HotelDataBase.amenities = amenities;
    }

    public static List<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public static void setRoomTypes(List<RoomType> roomTypes) {
        HotelDataBase.roomTypes = roomTypes;
    }

    RoomType SINGLE= new RoomType(1,"SINGLE","One Bed",300);
       RoomType DOUBLE= new RoomType(2,"DOUBLE","Two Beds",500);
       RoomType SUITE= new RoomType(3, "Suite", "Luxury room", 800);
       RoomType DELUXE= new RoomType (4, "Deluxe", "Large luxury room", 1000);
        public void populateDummyData(){
            //roomtypes array
            roomTypes.add(SINGLE);
            roomTypes.add(DOUBLE);
            roomTypes.add(SUITE);
            roomTypes.add(DELUXE);
            //amenities array
            Amenity wifi   = new Amenity(1,"WiFi",     "High-speed wireless internet",       0.0);
            Amenity tv     = new Amenity(2,"TV",       "50-inch smart TV with cable",        0.0);
            Amenity minibar= new Amenity(2,"Mini-bar", "Stocked mini-bar, refreshed daily", 50.0);
            Amenity pool   = new Amenity(3,"Pool",     "Access to rooftop infinity pool",  100.0);
            Amenity gym    = new Amenity(4,"Gym",      "24-hour fully equipped gym",         80.0);
            Amenity jacuzzi= new Amenity(5,"Jacuzzi",  "Private in-room jacuzzi",          150.0);
            amenities.add(wifi);
            amenities.add(tv);
            amenities.add(minibar);
            amenities.add(pool);
            amenities.add(gym);
            amenities.add(jacuzzi);
            //room
            rooms.add(new Room(1,"100" ,SINGLE,1,true));

            rooms.add(new Room (2,"101" ,SINGLE,1,false));
            rooms.add(new Room(3,"102" ,SINGLE,1,false));
            rooms.add(new Room(4,"200" ,DOUBLE,2,true));
            rooms.add(new Room(5,"201" ,DOUBLE,2,true));
            rooms.add(new Room(6,"202" ,DOUBLE,2,false));
            rooms.add(new Room(7,"300" ,SUITE,3,false));
            rooms.add(new Room(8,"301" ,SUITE,3,true));
            rooms.add(new Room(9,"302" ,SUITE,3,true));
            rooms.add(new Room(10,"400" ,DELUXE,4,true));
            rooms.add(new Room(11,"401" ,DELUXE,4,true));
            Room room1=rooms.get(0);
            room1.addAmenity(wifi);
            room1.addAmenity(tv);
            Room room2=rooms.get(1);
            room1.addAmenity(wifi);
            room1.addAmenity(tv);
            Room room3=rooms.get(2);
            Room room4=rooms.get(3);
            Room room5=rooms.get(4);
            Room room6=rooms.get(5);
            Room room7=rooms.get(6);
            Room room8=rooms.get(7);
            room8.addAmenity(wifi);
            room8.addAmenity(tv);
            room8.addAmenity(minibar);
            room8.addAmenity(pool);
            room8.addAmenity(jacuzzi);
            Room room9=rooms.get(8);
            Room room10=rooms.get(9);
            Room room11=rooms.get(10);
//        guests.add(new model.Guest(" Moaz Sherif"));
//        guests.add(new model.Guest("Yassen Ahmed"));
//        guests.add(new model.Guest("Amy George"));
            RoomPrefrences rp1=new RoomPrefrences(SINGLE,1,true);
            RoomPrefrences rp2=new RoomPrefrences(DOUBLE,2,true);
            RoomPrefrences rp3=new RoomPrefrences(SUITE,3,false);
            RoomPrefrences rp4=new RoomPrefrences(DELUXE,4,false);
            Guest Yassen = new Guest("Yassen_a","Yassen@1234", "Yassen Ahmed ",
                    LocalDate.of(2008, 3, 15), Gender.MALE, 5000.0, "Cairo, Egypt", rp1);
            Guest Amy  = new Guest("Amy_a",   "Amy@5678",  "Amy George",
                    LocalDate.of(2007, 7, 22), Gender.FEMALE, 8000.0, "Giza, Egypt",  rp2);
            Guest Moaz = new Guest("moaz_m",   "Moaz@9012",  "Moaz Sherif",
                    LocalDate.of(2008, 6, 11),  Gender.MALE, 10000, "Alexandria, Egypt", rp3);
            Guest layla = new Guest("layla_s",  "Layla@3456", "Layla Said",
                    LocalDate.of(2000, 11, 30),Gender.FEMALE, 12000.0, "Sharm El-Sheikh, Egypt", rp4);
            guests.add(Yassen);
            guests.add(Amy);
            guests.add(Moaz);



            Admin admin1 = new Admin("admin_1", "model.Admin@0001", "Joyce Sherif",
                    LocalDate.of(2007, 5, 15), Gender.FEMALE, Role.ADMIN,40);

            Receptionist recept1 = new Receptionist("recept_1", "Recept@0001", "Angelina Mike",
                    LocalDate.of(1993, 9, 14), Gender.FEMALE,Role.RECEPTIONIST ,35);

            Receptionist recept2 = new Receptionist("recept_2", "Recept@0002", "Youssef Fahmy",
                    LocalDate.of(1990, 7, 25), Gender.MALE,Role.RECEPTIONIST ,35);
              staff.add(admin1);
              staff.add(recept1);
              staff.add(recept2);
            Reservation res1 =new Reservation(1,room1,Yassen,LocalDate.now().minusDays( 1),LocalDate.now().plusDays(1));
            Reservation res2 =new Reservation(2,room8,Moaz,LocalDate.now().minusDays( 1),LocalDate.now().plusDays(7));
            Reservation res3 =new Reservation(3,room2,Amy,LocalDate.now().minusDays( 1),LocalDate.now().plusDays(4));
            res1.confirm();
            res2.confirm();
            res3.confirm();
            reservations.add(res1);
            reservations.add(res2);
            reservations.add(res3);
            Invoice inv1 = new Invoice(1,res1, PaymentMethod.CARD);
            inv1.markAsPaid();
            Invoices.add(inv1);
            Invoice inv2 = new Invoice(2,res2, PaymentMethod.CARD);
            inv2.markAsPaid();
            Invoices.add(inv2);
            Invoice inv3 = new Invoice(3,res3, PaymentMethod.CASH);









        }

    public Guest findGuestByUsername(String username) {
        return guests.stream()
                .filter(g -> g.getUsername().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }

    /** Find a staff member by username (case-insensitive), or null if not found. */
    public Staff findStaffByUsername(String username) {
        return staff.stream()
                .filter(s -> s.getUsername().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }

    /** Find a room by its room number (case-insensitive), or null if not found. */
    public Room findRoomByNumber(String roomNumber) {
        return rooms.stream()
                .filter(r -> r.getRoomNumber().equalsIgnoreCase(roomNumber))
                .findFirst().orElse(null);
    }

    /** Find a room by ID, or null if not found. */
    public Room findRoomById(int id) {
        return rooms.stream()
                .filter(r -> r.getRoomId() == id)
                .findFirst().orElse(null);
    }

    /** Find a reservation by ID, or null if not found. */
    public Reservation findReservationById(int id) {
        return reservations.stream()
                .filter(r -> r.getReservationId() == id)
                .findFirst().orElse(null);
    }





    }

