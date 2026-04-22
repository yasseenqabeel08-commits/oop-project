package model.interfaces;

import HotelRooms.Invoice;

public interface Payable {
    boolean pay (double amount);
    Invoice getinvoice();
}
