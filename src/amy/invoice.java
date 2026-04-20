

package amy;
import java.time.*;

public class invoice {
    private int invoiceId;
    private LocalDate issueDate;
    private double totalAmount;
    private boolean isPaid;
    private PaymentMethod paymentMethod;
    private Reservation reservation;

    public invoice(int Id, double amount) {
        invoiceId = Id;
        totalAmount = amount;
        this.issueDate = LocalDate.now();
        this.isPaid = false;
    }
    invoice(){}

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void generateinvoice() {
        System.out.println("Invoice " + invoiceId + " is being processed...");
    }
    public void markAsPaid() {
        this.isPaid = true;
        System.out.println("Payment confirmed for Invoice " + invoiceId);
    }
    public String printSummary() {
        String summary = "--- HOTEL INVOICE ---\n";
        summary += "ID: " + invoiceId + "\n";
        summary += "Date: " + issueDate + "\n";
        summary += "Amount Due: $" + totalAmount + "\n";


        if (isPaid) {
            summary += "Status: PAID\n";
        } else {
            summary += "Status: UNPAID\n";
        }  if (paymentMethod != null) {
            summary += "Method: " + paymentMethod + "\n";
        } else {
            summary += "Method: N/A\n";
        }
        return summary;}




    public static void main(String[] args) {

    }
}

