package model;

import HotelRooms.*;
import java.time.LocalDate;
import java.util.List;

import model.enums.*;

public class Receptionist extends Staff {

    public Receptionist(String username, String password,String name, LocalDate dateOfBirth, Gender gender, Role role, int workinghours){
        super(username, password,name, dateOfBirth, gender,role,workinghours);
    }
    public void checkIn(int reservationId){
        Reservation reservation=HotelDataBase.getInstance().findReservationById(reservationId);
        if(reservation!=null&& reservation.getStatus()==ReservationStatus.CONFIRMED){
            reservation.setStatus(ReservationStatus.CONFIRMED);
            System.out.println("Guest checked in for room:"+reservation.getRoom().getRoomNumber());
        }else{
            System.out.println("Failed to check in: Reservation is not confirmed");
        }
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
        return invoice;
    }
    public List<Reservation> viewAllReservations() {
        return List.copyOf(HotelDataBase.getInstance().getReservations());
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }
}