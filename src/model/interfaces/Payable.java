package model.interfaces;

import amy.Invoice;

public interface Payable {
    boolean pay (double amount);
    Invoice getinvoice();
}
