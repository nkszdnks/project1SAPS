package Entities.Transactions.Requests;

public abstract class TransactionRequest {
    private String fromIban,ExecutorID,reason;
    private String type;
    private double amount;
    public  TransactionRequest(String fromIban, double amount, String reason,String type,String ExecutorID) {
        this.fromIban = fromIban;
        this.ExecutorID = ExecutorID;
        this.amount = amount;
        this.reason = reason;
        this.type = type;
    }
    public String getFromIban() {
        return fromIban;
    }
    public void setFromIban(String fromIban) {
        this.fromIban = fromIban;
    }
    public String getExecutorID() {
        return ExecutorID;
    }
    public void setExecutorID(String ExecutorID) {
        this.ExecutorID = ExecutorID;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }
}
