package Entities.Transactions.Requests;

public class PaymentRequest extends TransactionRequest {

    private String RfCode;
    private double fee;
    public PaymentRequest(String fromIban,String RfCode,String ExecutorID) {
        super(fromIban, 0, "Bill Payment", "Payment",ExecutorID);
        this.RfCode = RfCode;
        this.fee = 0.0;

    }
    public PaymentRequest(String fromIban,String RfCode,String ExecutorID,double fee) {
        super(fromIban, 0, "Bill Payment", "Payment",ExecutorID);
        this.RfCode = RfCode;
        this.fee = fee;

    }

    public double getFee() {
        return fee;
    }

    public String getRfCode() {
        return RfCode;
    }
}