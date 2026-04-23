import model.Guest;
import model.HotelDataBase;
import model.RoomPrefrences;
import model.enums.*;
import HotelRooms.*;
import java.time.LocalDate;
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
        //  runInteractiveMenu();
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


            RoomPrefrences rp1=new RoomPrefrences(DB.getRoomTypes().get(0),1,true);
            Guest newGuest =   new Guest("bad_user","short@12233","Bad User",
                            LocalDate.of(1995,1,1),Gender.MALE,0,"cairo",rp1);
            newGuest.register(newGuest);

            RoomPrefrences rp2=new RoomPrefrences(DB.getRoomTypes().get(0),1,true);
            Guest newGuest1 =  new Guest("bad_user2","nocaps123","Bad User",
                            LocalDate.of(1995,1,1),Gender.MALE,0,"",rp2);
            newGuest1.register(newGuest1);

            RoomPrefrences rp3=new RoomPrefrences(DB.getRoomTypes().get(0),1,true);
            Guest newGuest2 =  new Guest("young_u","Young@123","Young User",
                            LocalDate.now().minusYears(16),Gender.FEMALE,0,"",rp3);
            newGuest2.register(newGuest2);
            RoomPrefrences rp4=new RoomPrefrences(DB.getRoomTypes().get(0),1,true);
                Guest dup = new Guest("ahmed_h","Ahmed@1234","Ahmed H2",
                        LocalDate.of(1993,2,1),Gender.MALE,100,"",rp4);
                dup.register(dup);

       }
    private static void section(String title) {
        System.out.println("\n──────────────────────────────────────────");
        System.out.println("  " + title);
        System.out.println("──────────────────────────────────────────");
    }
    }
