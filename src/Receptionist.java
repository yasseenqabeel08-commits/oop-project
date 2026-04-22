import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import amy.Reservation;
import model.enums.*;

public class Receptionist extends Staff {
    public Receptionist(){
        super();
    }
    public Receptionist(String username, String password, LocalDate dateOfBirth, Gender gender, Role role, int workinghours){
        super(username, password, dateOfBirth, gender,role,workinghours);
    }
    public void checkIn(Reservation reservation){
        if(reservation!=null&& reservation.getStatus()==ReservationStatus.CONFIRMED){
            reservation.setStatus(ReservationStatus.CONFIRMED);
            System.out.println("Guest checked in for room:"+reservation.getRoom().getRoomNumber());
        }else{
            System.out.println("Failed to check in: Reservation is not confirmed");
        }
    }
    public Invoice checkOut(Reservation reservation){
        double finalcost= reservation.calculateTotal();
        if(reservation.getRoom()!=null){
            reservation.getRoom().setIsAvailable(true);
        }
        reservation.completed();

        Invoicde invoice=new Invoice(reservation,finalcost);
            System.out.println("Check out total cost="+finalcost);
        return invoice;
    }

    public List<Reservation> viewAllreservations( Reservation[] R){
        List<Reservation> reservationsList = new ArrayList<>(Arrays.asList(R));
        for(int i=0;i<R.length;i++){
            System.out.println("Room:"+i+R[i]);
        }return reservationsList;
    }
}
//jjjjjjjj