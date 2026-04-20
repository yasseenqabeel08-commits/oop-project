import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import amy.Reservation;
import model.enums.*;


public class Guest extends Person {
private double balance;
private String address;
private RoomPreference roompreference;


public Guest(){
    super();
}
public Guest(String username,String password, LocalDate dateOfBirth,Gender gender,double balance, String address, RoomPreference roompreference){
        super( username, password, dateOfBirth,gender);
        this.balance=balance;
        this.address=address;
        this.roompreference=roompreference;
    }
    public String validatePassword(String inputpassword){
    if(this.password.equals(inputpassword)){
        System.out.println("Login Successful");
    }else{
        System.out.println("Wrong password");
    }return null;
    }

    public void register(ArrayList<Guest> guestList){
    Scanner input = new Scanner(System.in);
    System.out.println("Enter your name: ");
    this.username= input.nextLine();
    System.out.println("Enter your password: ");
    this.password = input.nextLine();
        guestList.add(this); // Line 6
System.out.println("Registeration Successful");
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
public Reservation makeReservation(Room rooms, LocalDate startdate, LocalDate enddate){
    if(rooms.isIsAvailable){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name=input.nextLine();
        int id= (int)(Math.random()*1000);
      Reservation res= new Reservation(id);
      res.setRoom(rooms);
      res.setCheckInDate(startdate);
      res.setCheckOutDate(enddate);
      rooms.setIsAvailable(false);
      res.confirm();
      System.out.println("Reservation Successful for"+ name);
      return res;
    }else{
        System.out.println("No available rooms");
        return null;
    }
}
public void cancelReservarion(){


    }

public Invoice checkout();
}
