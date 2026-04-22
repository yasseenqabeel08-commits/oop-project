import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import model.enums.*;

public abstract class Staff extends Person {
    protected Role role;
    protected int workinghours;

    public Staff() {
        super();
    }

    public Staff(String username, String password, LocalDate dateOfBirth, Gender gender, Role role, int workinghours) {
        super(username, password, dateOfBirth, gender);
        this.role = role;
        this.workinghours = workinghours;
    }

    public boolean login() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String typedname = input.nextLine();
        System.out.println("Enter your password: ");
        String typedPassword = input.nextLine();
        if (typedname.equals(this.username) && typedPassword.equals(this.password)) {
            System.out.println("Login Successful");
            return true;
        } else {
            return false;
        }
    }

    public List<Guest> viewAllGuests(Guest[] guests) {
        List<Guest> guestList = new ArrayList<>(Arrays.asList(guests));
        for (int i = 0; i < guests.length; i++) {
            System.out.println("Guest:" + i + guests[i]);
        }
        return guestList;
    }

    public List<Room> viewAllRooms(Room[] R) {
        List<Room> roomList = new ArrayList<>(Arrays.asList(R));
        for (int i = 0; i < R.length; i++) {
            System.out.println("Room:" + i + R[i]);
        }
        return roomList;
    }

    public void setRole(Role role){
            this.role=role;
    }
    public void setWorkinghours(int workinghours){
        this.workinghours=workinghours;
    }

    public Role getRole() {
        return role;
    }
    public int getWorkinghours() {return workinghours;}
}
