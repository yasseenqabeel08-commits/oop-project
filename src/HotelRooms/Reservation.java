package HotelRooms;
import model.Guest;
import model.enums.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class Reservation {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private int reservationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;
    private double totalCost;
    private Room room;
    private Guest guest;
    private final long nights;
    private Invoice invoice;
    public Reservation(int reservationId,Room room,Guest guest,LocalDate checkInDate,LocalDate checkOutDate){
        if (guest == null)         throw new IllegalArgumentException("Guest cannot be null.");
        if (room  == null)         throw new IllegalArgumentException("Room cannot be null.");
        if (checkInDate  == null)  throw new IllegalArgumentException("Check-in date cannot be null.");
        if (checkOutDate == null)  throw new IllegalArgumentException("Check-out date cannot be null.");
        if (!checkOutDate.isAfter(checkInDate))
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
//        if (!room.isAvailable())
//            throw new IllegalStateException("Room " + room.getRoomNumber() + " is not available.");
        this.reservationId=reservationId;
        this.room=room;
        this.guest=guest;
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
        this.nights        = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        this.status        = ReservationStatus.PENDING;
        this.totalCost     = room.calculateCost(this.nights);

// Mark room as occupied immediately upon reservation creation
        room.setAvailable(false);

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

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public double calculateTotal(){
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);


        if (nights <= 0) {
            nights = 1;
        }
        double roomCost = nights * room.getRoomtype().getBasePrice();
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
        if (status != ReservationStatus.PENDING) {
            throw new IllegalStateException("Only pending reservations can be confirmed.");
        }
        this.status = ReservationStatus.CONFIRMED;
    }
    public void completed() {
        this.status = ReservationStatus.COMPLETED;
        System.out.println("Reservation " + reservationId + " is now completed.");
    }

    public void cancel() {

            if (status == ReservationStatus.CANCELLED) {
                throw new IllegalStateException("Reservation already cancelled.");
            }

            if (status == ReservationStatus.COMPLETED) {
                throw new IllegalStateException("Cannot cancel a completed reservation.");
            }
        if (LocalDate.now().isAfter(checkInDate)) {
            throw new IllegalStateException("Cannot cancel after stay has started.");
        }

            this.status = ReservationStatus.CANCELLED;

            // make room available again
            if (room != null) {
                room.setAvailable(true);
            }

    }
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }
    public String toString() {
        return String.format(
                "[Reservation #%d] %-15s | Room %s | %s → %s | %d nights | EGP %.2f | %s",
                reservationId,
                guest.getUsername(),
                room.getRoomNumber(),
                checkInDate.format(FMT),
                checkOutDate.format(FMT),
                nights,
                totalCost,
                status);
    }
}




