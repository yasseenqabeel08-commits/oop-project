import javax.management.relation.Role;
import java.time.LocalDate;
import model.enums.*;

public class Receptionist extends Staff {
    public Receptionist(){
        super();
    }
    public Receptionist(String username, String password, LocalDate dateOfBirth, Gender gender, Role role, int workinghours,){
        super(username, password, dateOfBirth, gender,role,workinghours);
    }
    public void checkIn(Reservation reservation);
    public Invoice checkOut(Reservation reservation);
    public List<reservation> viewAllreservations;
}
//jjjjjjjj
