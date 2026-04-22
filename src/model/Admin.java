package model;

import java.time.LocalDate;

import amy.Room;
import model.enums.*;

public class Admin extends Staff  {


    @Override
    public void validatePassword(String password) {

    }

    public Admin(String username, String password,String name, LocalDate dateOfBirth, Gender gender, Role role, int workinghours){
        super(username, password,name, dateOfBirth, gender,role,workinghours);

    }

    public void addRoom(Room room) {

    }

    public void updateRoom(Room room) {

    }

    public void manageAmenities() {

    }

    public void manageRoomTypes() {

    }
}
