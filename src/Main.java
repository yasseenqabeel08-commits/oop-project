import model.*;
//import model.HotelDataBase;
//import model.RoomPrefrences;
import model.enums.*;
import HotelRooms.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final HotelDataBase DB = HotelDataBase.getInstance();
    private static final Scanner SC = new Scanner(System.in);
    public static void main(String[] args) {



        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║    Hotel Reservation System — Milestone 1  ║");
        System.out.println("║       CSE241 · Ain Shams University        ║");
        System.out.println("╚═══════════════════════════════════════════╝");
        System.out.println();

        DB.populateDummyData();
        System.out.println();

        runDemoScenarios();

        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println("  INTERACTIVE MENU");
        System.out.println("══════════════════════════════════════════");
         runInteractiveMenu();
    }
        private static void runDemoScenarios() {
            System.out.println("══════════════════════════════════════════");
            System.out.println("  DEMO SCENARIO WALKTHROUGH");
            System.out.println("══════════════════════════════════════════\n");

            // ── Scenario 1: Guest Registration ────────────────────────────────────
            section("Scenario 1: New Guest Registration");
            try {
                RoomPrefrences rp1=new RoomPrefrences(DB.getRoomTypes().get(0),1,true);
                Guest newGuest = new Guest(
                        "mona_a", "Mona@9999", "Mona Ahmed",
                        LocalDate.of(2000, 5, 10), Gender.FEMALE,
                        6000.0, "Luxor, Egypt",rp1 );
                newGuest.register(newGuest);
                System.out.println("  " + newGuest);
            } catch (Exception e) {
                System.out.println("  [ERROR] " + e.getMessage());
            }
            // ── Scenario 2: Validation Errors ────────────────────────────────────
            section("Scenario 2: Validation Error Examples");


            try {
                RoomPrefrences rp1=new RoomPrefrences(DB.getRoomTypes().get(0),1,true);

            Guest newGuest =   new Guest("bad_user","short@12233","Bad User",
                            LocalDate.of(1995,1,1),Gender.MALE,0,"cairo",rp1);
            newGuest.register(newGuest);
            } catch (Exception e) {
                e.printStackTrace();
            }

           try {
               RoomPrefrences rp2 = new RoomPrefrences(DB.getRoomTypes().get(0), 1, true);
               Guest newGuest1 = new Guest("bad_user2", "nocaps123", "Bad User",
                       LocalDate.of(1995, 1, 1), Gender.MALE, 0, "", rp2);
               newGuest1.register(newGuest1);
           } catch (Exception e) {
               e.printStackTrace();
           }

           try {
               RoomPrefrences rp3 = new RoomPrefrences(DB.getRoomTypes().get(0), 1, true);
               Guest newGuest2 = new Guest("young_u", "Young@123", "Young User",
                       LocalDate.now().minusYears(16), Gender.FEMALE, 0, "", rp3);
               newGuest2.register(newGuest2);
           } catch (Exception e) {
                e.printStackTrace();
           }
           try {
               RoomPrefrences rp4 = new RoomPrefrences(DB.getRoomTypes().get(0), 1, true);

               Guest dup = new Guest("ahmed_h", "Ahmed@1234", "Ahmed H2",
                       LocalDate.of(1993, 2, 1), Gender.MALE, 100, "", rp4);
               dup.register(dup);
           } catch (Exception e) {
               e.printStackTrace();
           }

       }
    private static void section(String title) {
        System.out.println("\n──────────────────────────────────────────");
        System.out.println("  " + title);
        System.out.println("──────────────────────────────────────────");
    }
    private static void runInteractiveMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n┌─────────────────────────────────────────┐");
            System.out.println("│           MAIN MENU                     │");
            System.out.println("├─────────────────────────────────────────┤");
            System.out.println("│  1. Login as Guest                      │");
            System.out.println("│  2. Login as Staff                      │");
            System.out.println("│  3. Register New Guest                  │");
            System.out.println("│  4. View All Available Rooms            │");
            System.out.println("│  0. Exit                                │");
            System.out.println("└─────────────────────────────────────────┘");
            System.out.print("  Choice: ");

            String choice = SC.nextLine().trim();
            switch (choice) {
                case "1" -> guestMenu();
               case "2" -> staffMenu();
               case "3" -> registerGuestMenu();
               case "4" -> printAllAvailableRooms();
                case "0" -> {
                    running = false;
                    System.out.println("  Goodbye!");
                }
                default  -> System.out.println("  Invalid choice. Please try again.");
            }
        }
    }
    private static void guestMenu() {
        System.out.print("  Username: ");
        String user = SC.nextLine().trim();
        System.out.print("  Password: ");
        String pass = SC.nextLine().trim();

        Guest g = DB.findGuestByUsername(user);

        if (g==null) {
            System.out.println("  [ERROR] Invalid guest username or password.");
            return;
        }
        System.out.println("  Welcome, " + g.getName() + "!");

        boolean active = true;
        while (active) {
            System.out.println("\n  ── Guest Menu ──");
            System.out.println("  1. View available rooms");
            System.out.println("  2. Make a reservation");
            System.out.println("  3. View my reservations");
            System.out.println("  4. Cancel a reservation");
            System.out.println("  5. Checkout (self)");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (SC.nextLine().trim()) {
                case "1" -> g.viewAvailableRooms().forEach(r -> System.out.println("    " + r));
                case "2" -> {
                    System.out.print("  Room number: ");
                    String rn = SC.nextLine().trim();
                    Room room = DB.findRoomByNumber(rn);
                    if (room == null || !room.isAvailable()) {
                        System.out.println("  Room not available.");
                        break;
                    }
                    try {
                        System.out.print("  Check-in  (YYYY-MM-DD): ");
                        LocalDate ci = LocalDate.parse(SC.nextLine().trim());
                        System.out.print("  Check-out (YYYY-MM-DD): ");
                        LocalDate co = LocalDate.parse(SC.nextLine().trim());
                        Reservation res = g.makeReservation(room, ci, co);
                        System.out.println("  " + res);
                    } catch (Exception e) {
                        System.out.println("  [ERROR] " + e.getMessage());
                    }
                }
                case "3" -> g.viewMyReservations().forEach(r -> System.out.println("    " + r));
                case "4" -> {
                    System.out.print("  Reservation ID: ");
                    try {
                        int id = Integer.parseInt(SC.nextLine().trim());
                        g.cancelReservation(id);
                    } catch (Exception e) {
                        System.out.println("  [ERROR] " + e.getMessage());
                    }
                }
                case "5" -> {
                    System.out.print("  Reservation ID: ");
                    try {
                        int id = Integer.parseInt(SC.nextLine().trim());
                        System.out.println("  Payment: 1=Cash  2=Card  3=Balance");
                        System.out.print("  Choice: ");
                        PaymentMethod pm = switch (SC.nextLine().trim()) {
                            case "1" -> PaymentMethod.CASH;
                            case "2" -> PaymentMethod.CARD;
                            default  -> PaymentMethod.BALANCE;
                        };
                        g.checkOut(id, pm);
                    } catch (Exception e) {
                        System.out.println("  [ERROR] " + e.getMessage());
                    }
                }
                case "0" -> active = false;
                default  -> System.out.println("  Invalid choice.");
            }
        }
    }
    private static void staffMenu() {
        System.out.print("  Username: ");
        String user = SC.nextLine().trim();
        System.out.print("  Password: ");
        String pass = SC.nextLine().trim();

        Staff s = DB.findStaffByUsername(user);
        if (s==null) {
            System.out.println("  [ERROR] Invalid staff credentials.");
            return;
        }
        System.out.println("  Welcome, " + s.getName() + " (" + s.getRole() + ")");

        if (s instanceof Admin a) adminMenu(a);
        else if (s instanceof Receptionist r) receptionistMenu(r);
    }
    private static void adminMenu(Admin admin) {
        boolean active = true;
        while (active) {
            System.out.println("\n  ── Admin Menu ──");
            System.out.println("  1. View all guests");
            System.out.println("  2. View all rooms");
            System.out.println("  3. View all room types");
            System.out.println("  4. View all amenities");
            System.out.println("  5. View all reservations");
            System.out.println("  6. View all invoices");
            System.out.println("  7. Add room type");
            System.out.println("  8. Add amenity");
            System.out.println("  9. Delete room by ID");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (SC.nextLine().trim()) {
                case "1" -> admin.viewAllGuests().forEach(g -> System.out.println("    " + g));
                case "2" -> admin.viewAllRooms().forEach(r -> System.out.println("    " + r));
                case "3" -> admin.viewAllRoomTypes().forEach(t -> System.out.println("    " + t));
                case "4" -> admin.viewAllAmenities().forEach(a -> System.out.println("    " + a));
                case "5" -> admin.viewAllReservations().forEach(r -> System.out.println("    " + r));
                case "6" -> admin.viewAllInvoices().forEach(i -> System.out.println("    " + i));
                case "7" -> {
                    try {
                        System.out.print("  Type name: "); String name = SC.nextLine().trim();
                        System.out.print("  Description: "); String desc = SC.nextLine().trim();
                        System.out.print("  Base price: "); double price = Double.parseDouble(SC.nextLine().trim());
                        admin.addRoomType(new RoomType(5,name, desc, price));
                    } catch (Exception e) { System.out.println("  [ERROR] " + e.getMessage()); }
                }
                case "8" -> {
                    try {
                        System.out.print("  Amenity name: "); String name = SC.nextLine().trim();
                        System.out.print("  Description: ");  String desc = SC.nextLine().trim();
                        System.out.print("  Daily cost: ");   double cost = Double.parseDouble(SC.nextLine().trim());
                        admin.addAmenity(new Amenity(6,name, desc, cost));
                    } catch (Exception e) { System.out.println("  [ERROR] " + e.getMessage()); }
                }
                case "9" -> {
                    try {
                        System.out.print("  Room ID to delete: ");
                        int id = Integer.parseInt(SC.nextLine().trim());
                        admin.deleteRoom(id);
                    } catch (Exception e) { System.out.println("  [ERROR] " + e.getMessage()); }
                }
                case "0" -> active = false;
                default  -> System.out.println("  Invalid choice.");
            }
        }
    }

    private static void receptionistMenu(Receptionist recept) {
        boolean active = true;
        while (active) {
            System.out.println("\n  ── Receptionist Menu ──");
            System.out.println("  1. View all reservations");
            System.out.println("  2. Check in guest");
            System.out.println("  3. Check out guest");
            System.out.println("  4. View pending reservations");
            System.out.println("  5. Confirm reservation");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (SC.nextLine().trim()) {
                case "1" -> recept.viewAllReservations().forEach(r -> System.out.println("    " + r));
                case "2" -> {
                    System.out.print("  Reservation ID: ");
                    try { recept.checkIn(Integer.parseInt(SC.nextLine().trim())); }
                    catch (Exception e) { System.out.println("  [ERROR] " + e.getMessage()); }
                }
                case "3" -> {
                    try {
                        System.out.print("  Reservation ID: ");
                        int id = Integer.parseInt(SC.nextLine().trim());
                        System.out.println("  Payment: 1=Cash  2=Card  3=Balance");
                        System.out.print("  Choice: ");
                        PaymentMethod pm = switch (SC.nextLine().trim()) {
                            case "1" -> PaymentMethod.CASH;
                            case "2" -> PaymentMethod.CARD;
                            default  -> PaymentMethod.BALANCE;
                        };
                        recept.checkOut(id, pm);
                    } catch (Exception e) { System.out.println("  [ERROR] " + e.getMessage()); }
                }
                case "4" ->
                    recept.viewPendingReservations().forEach(r -> System.out.println("    " + r));
                case "5" -> {
                    try {
                        System.out.print("  Reservation ID: ");
                        int id = Integer.parseInt(SC.nextLine().trim());
                        recept.confirmReservation(id);
                    } catch (Exception e) {
                        System.out.println("  [ERROR] " + e.getMessage());
                    }
                }

                case "0" -> active = false;
                default  -> System.out.println("  Invalid choice.");
            }
        }
    }
    private static void registerGuestMenu() {
        System.out.println("  ── Register New Guest ──");
        try {
            System.out.print("  Username: ");    String user = SC.nextLine().trim();
            System.out.print("  Password: ");    String pass = SC.nextLine().trim();
            System.out.print("  Full name: ");   String name = SC.nextLine().trim();
            System.out.print("  DOB (YYYY-MM-DD): "); LocalDate dob = LocalDate.parse(SC.nextLine().trim());
            System.out.print("  Gender (M/F): "); Gender g = SC.nextLine().trim().equalsIgnoreCase("F")
                    ? Gender.FEMALE : Gender.MALE;
            System.out.print("  Initial balance: "); double bal = Double.parseDouble(SC.nextLine().trim());
            System.out.print("  Address: ");      String addr = SC.nextLine().trim();
            System.out.print("  SmokingAllowed: ");        String sa= SC.nextLine().trim();
            System.out.print(" Room Type: SINGLE , DOUBLE ,SUITE , DULEX ");   String rt= SC.nextLine().trim();
            RoomPrefrences rp1=new RoomPrefrences(DB.getRoomTypes().stream().filter(roomType -> roomType.getTypeName().equals(rt)).findFirst().orElse(null), 1,Boolean.getBoolean(sa));
            Guest guest = new Guest(user, pass, name, dob, g, bal, addr,rp1);
            guest.register(guest);
        } catch (Exception e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }
    private static void printAllAvailableRooms() {
        List<Room> available = DB.getRooms().stream()
                .filter(Room::isAvailable).toList();
        if (available.isEmpty()) {
            System.out.println("  No rooms currently available.");
        } else {
            System.out.printf("  %d room(s) available:%n", available.size());
            available.forEach(r -> System.out.println("    " + r));
        }
    }
    }
