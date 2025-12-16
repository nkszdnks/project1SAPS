package Entities.Transactions.Requests;

public class PaymentRequest extends TransactionRequest {

    private String RfCode;
    public PaymentRequest(String fromIban,String RfCode,String ExecutorID) {
        super(fromIban, 0, "Bill Payment", "Payment",ExecutorID);
        this.RfCode = RfCode;

    }

    public String getRfCode() {
        return RfCode;
    }
}