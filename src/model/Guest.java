package model;
import HotelRooms.*;
import model.enums.*;
import model.interfaces.Payable;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class Guest extends Person implements Payable {
    private double balance;
    private String address;
    private RoomPrefrences roompreference;
    private Invoice     lastInvoice;

//    public Guest(){
//        super();
//    }
    public Guest(String username,String password,String name, LocalDate dateOfBirth,Gender gender,double balance, String address, RoomPrefrences roompreference){
        super( username, password, name, dateOfBirth,gender);//added string name as it was missing
        if (balance < 0)
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        this.balance=balance;
        this.address=address;
        this.roompreference=roompreference;

    }



    public void register(Guest guest) {
        HotelDataBase db = HotelDataBase.getInstance();
        boolean taken = db.getGuests().stream()
                .anyMatch(g -> g.getUsername().equalsIgnoreCase(guest.getUsername()));
        if (taken)
            throw new IllegalStateException("Username '" + username + "' is already taken.");
        db.getGuests().add(guest);
        System.out.println("[REGISTER] Guest '" + username + "' registered successfully.");
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equalsIgnoreCase(username) && this.password.equals(password);
    }

    public List<Room> viewAvailableRooms() {
        return HotelDataBase.getInstance().getRooms().stream()
                .filter(Room::isAvailable)
                .collect(Collectors.toList());
    }
    public List<Room> viewRoomsMatchingPreferences() {
        return HotelDataBase.getInstance().getRooms().stream()
                .filter(Room::isAvailable)
                .filter(r -> roompreference.getPreferredType() == null ||
                        r.getRoomtype().getTypeId() ==
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

        Reservation res = new Reservation(5, room,this, checkInDate, checkOutDate);
        HotelDataBase.getInstance().getReservations().add(res);
        System.out.printf("[RESERVATION] %s reserved Room %s (%s → %s) | EGP %.2f%n",
                username, room.getRoomNumber(),
                checkInDate, checkOutDate, res.getTotalCost());
        return res;
    }
    public void cancelReservation(int reservationId) {
        Reservation res = HotelDataBase.getInstance().getReservations().stream()
                .filter(r -> r.getReservationId() == reservationId &&
                        r.getGuest().getUsername().equals(this.username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No reservation #" + reservationId + " found for guest " + username));

        res.cancel();
        System.out.printf("[CANCEL] Reservation #%d cancelled for %s.%n", reservationId, username);
    }

    public List<Reservation> viewMyReservations() {
        return HotelDataBase.getInstance().getReservations().stream()
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
    public Invoice getinvoice() {
        return lastInvoice;
    }



    // ── Getters ───────────────────────────────────────────────────────────────

    public double         getBalance()          { return balance;         }
    public String         getAddress()          { return address;         }
    public RoomPrefrences getRoomPreferences()  { return roompreference; }

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
    public Invoice checkOut(int reservationId, PaymentMethod method) {
        Reservation res = HotelDataBase.getInstance().getReservations().stream()
                .filter(r -> r.getReservationId() == reservationId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Reservation #" + reservationId + " not found."));

        if (res.getStatus() != ReservationStatus.CONFIRMED)
            throw new IllegalStateException(
                    "Cannot check out — reservation #" + reservationId
                            + " must be CONFIRMED (current: " + res.getStatus() + ").");

        double total = res.calculateTotal();
        Guest  guest = res.getGuest();

        if (method == PaymentMethod.BALANCE) {
            boolean paid = guest.pay(total);
            if (!paid)
                throw new IllegalStateException(
                        String.format("Guest '%s' has insufficient balance. Required: EGP %.2f",
                                guest.getUsername(), total));
        }

        Invoice invoice = new Invoice(4,res, method);
        invoice.markAsPaid();
        res.completed();

        HotelDataBase.getInstance().getInvoices().add(invoice);

        System.out.printf("[CHECK-OUT] Guest '%s' checked out from Room %s.%n",
                guest.getUsername(), res.getRoom().getRoomNumber());
        System.out.println(invoice.printSummary());
        this.lastInvoice = invoice;
        return invoice;
    }

    public void setAddress(String address)           { this.address       = address       == null ? "" : address.trim();       }
    public void setRoomPreferences(RoomPrefrences p) { this.roompreference = p; }


    // ── Utility ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("[Guest] %s | %s | Balance: EGP %.2f | %s",
                username, name, balance, gender);
    }

}
