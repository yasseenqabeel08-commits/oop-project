package model;
import amy.*;
import java.time.LocalDate;
import java.util.List;
import model.enums.*;

public class Receptionist extends Staff {


    @Override
    public void validatePassword(String password) {

    }

    public Receptionist(String username, String password,String name, LocalDate dateOfBirth, Gender gender, Role role, int workinghours){
        super(username, password,name, dateOfBirth, gender,role,workinghours);
    }

    public void checkIn(Reservation reservation) {

    }

    public Invoice checkOut(Reservation reservation) {
        return null;
    }

    public List<Reservation> viewAllReservations(){
        return null;
    }
}

