package model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import HotelRooms.Invoice;
import HotelRooms.Reservation;
import HotelRooms.Room;
import model.enums.*;

public class Guest extends Person {
private double balance;
private String address;
private RoomPrefrences roompreference;

//public Guest(){
//    super();
//}
public Guest(String username,String password,String name, LocalDate dateOfBirth,Gender gender,double balance, String address, RoomPrefrences roompreference){

        super( username, password,name,dateOfBirth,gender);
        this.balance=balance;
        this.address=address;
        this.roompreference=roompreference;
    }
    public void validatePassword(String inputpassword){
    if(this.password.equals(inputpassword)){
        System.out.println("Login Successful");
    }else{
        System.out.println("Wrong password");
    }
    }

    public void register( Guest guest){
        HotelDataBase db = HotelDataBase.getInstance();
        boolean taken = db.getGuests().stream()
                .anyMatch(g -> g.getUsername().equalsIgnoreCase(this.username));
        if (taken)
            throw new IllegalStateException("Username '" + username + "' is already taken.");
        db.getGuests().add(this);
        System.out.println("[REGISTER] Guest '" + username + "' registered successfully.");
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
public List<Room> viewAvailablerooms(Room[] rooms){
for (int i=0;i<rooms.length;i++){
    if(rooms[i]!=null){
        System.out.println("Available rooms:"+rooms[i]);
    }else{
        System.out.println("No available rooms");
    }
}return null;
}
public Reservation makeReservation(){
    return null;
}
public void cancelReservarion(){

};
public Invoice checkout(){
    return null;
}
}
