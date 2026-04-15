import javax.management.relation.Role;
import java.time.LocalDate;

public class Admin extends Staff {
    public Admin(){
        super();
    }
    public Admin(String username, String password, LocalDate dateOfBirth, Gender gender, Role role, int workinghours,){
        super(username, password, dateOfBirth, gender,role,workinghours);
    }
    public void addRoom(Room room);
    public void updateRoom(Room room);
    public void manageAmenities();
    public void manageRoomTypes();
}
