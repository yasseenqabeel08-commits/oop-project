package model;

import java.time.LocalDate;
import java.util.Scanner;

import amy.Room;
import model.enums.*;

public abstract class Staff extends Person {
    protected Role role;
    protected int workinghours;

    public Staff(String username, String password,String name, LocalDate dateOfBirth,Gender gender, Role role, int workinghours) {
        super(username, password,name, dateOfBirth, gender);
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
        }
        return false;
    }
    public void  viewAllGuests(Guest[] guests){
      for(int i=0;i<guests.length;i++){
          System.out.println("model.Guest:"+i+guests[i]);
      }
    }

    public void viewAllRooms(Room[] R){
        for(int i=0;i<R.length;i++){
          System.out.println("Room:"+i+R[i]);
        }
    };

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
