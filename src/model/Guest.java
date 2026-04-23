import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import amy.Invoice;
import amy.Reservation;
import amy.Room;
import model.Person;
import model.enums.*;

public class Guest extends Person {
private double balance;
private String address;
private RoomPreference roompreference;
    private Invoice     lastInvoice;

public Guest(){
    super();
}
public Guest(String username,String password,String name, LocalDate dateOfBirth,Gender gender,double balance, String address, RoomPreference roompreference){
        super( username, password, name, dateOfBirth,gender);//added string name as it was missing
    if (balance < 0)
        throw new IllegalArgumentException("Initial balance cannot be negative.");
    this.balance=balance;
        this.address=address;
        this.roompreference=roompreference;
    }



    public void register() {
        HotelDatabase db = HotelDatabase.getInstance();
        boolean taken = db.getGuests().stream()
                .anyMatch(g -> g.getUsername().equalsIgnoreCase(this.username));
        if (taken)
            throw new IllegalStateException("Username '" + username + "' is already taken.");
        db.getGuests().add(this);
        System.out.println("[REGISTER] Guest '" + username + "' registered successfully.");
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equalsIgnoreCase(username) && this.password.equals(password);
    }

    public List<Room> viewAvailableRooms() {
        return HotelDatabase.getInstance().getRooms().stream()
                .filter(Room::isAvailable)
                .collect(Collectors.toList());
    }
    public List<Room> viewRoomsMatchingPreferences() {
        return HotelDatabase.getInstance().getRooms().stream()
                .filter(Room::isAvailable)
                .filter(r -> roompreference.getPreferredType() == null ||
                        r.getRoomType().getTypeId() ==
                                roompreference.getPreferredType().getTypeId())
                .filter(r -> roompreference.getPreferredFloor() == 0 ||
                        r.getFloor() == roompreference.getPreferredFloor())
                .filter(r -> !roompreference.isSmokingAllowed() || r.isSmokingAllowed())
                .collect(Collectors.toList());
    }

    public Reservation makeReservation(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        if (room == null)
            throw new IllegalArgumentException("Room cannot be null.");
        if (!room.isAvailable())
            throw new IllegalStateException("Room " + room.getRoomNumber() + " is not available.");

        Reservation res = new Reservation(this, room, checkInDate, checkOutDate);
        HotelDatabase.getInstance().getReservations().add(res);
        System.out.printf("[RESERVATION] %s reserved Room %s (%s → %s) | EGP %.2f%n",
                username, room.getRoomNumber(),
                checkInDate, checkOutDate, res.getTotalCost());
        return res;
    }
    public void cancelReservation(int reservationId) {
        Reservation res = HotelDatabase.getInstance().getReservations().stream()
                .filter(r -> r.getReservationId() == reservationId &&
                        r.getGuest().getUsername().equals(this.username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No reservation #" + reservationId + " found for guest " + username));

        res.cancel();
        System.out.printf("[CANCEL] Reservation #%d cancelled for %s.%n", reservationId, username);
    }
    public List<Reservation> viewMyReservations() {
        return HotelDatabase.getInstance().getReservations().stream()
                .filter(r -> r.getGuest().getUsername().equals(this.username))
                .collect(Collectors.toList());
    }
    public List<Reservation> viewMyReservations() {
        return HotelDatabase.getInstance().getReservations().stream()
                .filter(r -> r.getGuest().getUsername().equals(this.username))
                .collect(Collectors.toList());
    }
    @Override
    public boolean pay(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Payment amount must be > 0.");
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public Invoice getInvoice() {
        return lastInvoice;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public double         getBalance()          { return balance;         }
    public String         getAddress()          { return address;         }
    public String         getPhoneNumber()      { return phoneNumber;     }
    public RoomPreference getRoomPreferences()  { return roomPreferences; }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setBalance(double balance) {
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative.");
        this.balance = balance;
    }

    public void topUpBalance(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Top-up amount must be > 0.");
        this.balance += amount;
        System.out.printf("[BALANCE] %s topped up by EGP %.2f. New balance: EGP %.2f%n",
                username, amount, balance);
    }

    public void setAddress(String address)           { this.address       = address       == null ? "" : address.trim();       }
    public void setPhoneNumber(String phoneNumber)   { this.phoneNumber   = phoneNumber   == null ? "" : phoneNumber.trim();   }
    public void setRoomPreferences(RoomPreference p) { this.roomPreferences = p; }

    // ── Utility ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("[Guest] %s | %s | Balance: EGP %.2f | %s",
                username, name, balance, gender);
    }

}
