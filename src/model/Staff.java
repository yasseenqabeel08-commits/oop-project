import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import amy.Room;
import model.enums.*;

public abstract class Staff extends Person {
    protected Role role;
    protected int workinghours;

    public Staff(){
        super();
    }
    public Staff(String username, String password, LocalDate dateOfBirth,Gender gender, Role role, int workinghours) {
        super(username, password, dateOfBirth, gender);
        if (role == null)
            throw new IllegalArgumentException("Role cannot be null.");
        if (workinghours < 0 || workinghours > 168)
            throw new IllegalArgumentException("Working hours must be between 0 and 168 per week.");
        this.role = role;
        this.workinghours = workinghours;
    }
    public boolean login(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String typedname= input.nextLine();
        System.out.println("Enter your password: ");
        String typedPassword=input.nextLine();
        if(typedname.equals(this.username) && typedPassword.equals(this.password)){
            System.out.println("Login Successful");
            return true;
        }else{return false;}
    }

    public List<Guest> viewAllGuests() {
        return List.copyOf(HotelDatabase.getInstance().getGuests());
    }


    public List<Room> viewAllRooms() {
        return List.copyOf(HotelDatabase.getInstance().getRooms());
    }

    public List<Reservation> viewAllReservations() {
        return List.copyOf(HotelDatabase.getInstance().getReservations());
    }
    public void setRole(Role role){
            this.role=role;
    }
    public void setWorkinghours(int workinghours){
        if (workinghours < 0 || workinghours > 168)
            throw new IllegalArgumentException("Working hours must be between 0 and 168.");
        this.workinghours=workinghours;
    }

    public Role getRole() {
        return role;
    }
    public int getWorkinghours() {return workinghours;}

    @Override
    public String toString() {
        return String.format("[Staff] %-20s | %-15s | Role: %-15s | %dh/week",
                username, name, role, workinghours);
    }
}
