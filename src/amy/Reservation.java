package amy;
import model.*;
import model.Guest;
import model.enums.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class Reservation {
    private int reservationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;
    private double totalCost;
    private Room room;
    private Guest guest;


    public Reservation(int reservationId,Room room,Guest guest,LocalDate checkInDate,LocalDate checkOutDate){
        this.reservationId=reservationId;
        this.room=room;
        this.guest=guest;
        this.checkInDate=LocalDate.now();
        this.checkOutDate=LocalDate.now();

    }
public void setRoom(Room room){
        this.room=room;
}

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getReservationId() {
        return reservationId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double calculateTotal(){
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);


        if (nights <= 0) {
            nights = 1;
        }
        double roomCost = nights * room.getType().getBasePrice();
        double amenitiesCost = 0.0;
        if (room.getAmenities() != null) {
            ArrayList<Amenity> amenityList = room.getAmenities();
            for (int i = 0; i < amenityList.size(); i++) {
                Amenity a = amenityList.get(i);
                amenitiesCost += a.getCost();
            }
        }

        this.totalCost = roomCost + amenitiesCost;
        return this.totalCost;
    }
public Room getRoom() {
        return room;
}


    public void confirm() {
        this.status = ReservationStatus.CONFIRMED;
        System.out.println("Reservation " + reservationId + " is now confirmed.");
    }
    public void completed() {
        this.status = ReservationStatus.COMPLETED;
        System.out.println("Reservation " + reservationId + " is now completed.");
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
        if (this.room != null) {
            this.room.setIsAvailable(true);
        }
        System.out.println("Reservation " + reservationId + " has been cancelled.");
    }
}




