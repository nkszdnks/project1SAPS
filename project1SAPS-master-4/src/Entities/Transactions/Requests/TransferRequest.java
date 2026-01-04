package Entities.Transactions.Requests;

public class TransferRequest extends TransactionRequest {
    public enum Rail { LOCAL, SEPA, SWIFT }

    private String toIban;
    private Rail rail;
    private double fee;


    public TransferRequest(String fromIban, String toIban, double amount, Rail rail, String ExecutorID, String reason) {
        super(fromIban,amount,String.valueOf(rail)+" Transfer:"+ reason,"Transfer",ExecutorID);
        this.toIban = toIban;
        this.rail = rail;
        this.fee = 0.0;
    }

    public double getFee() {
        return fee;
    }

    public TransferRequest(String fromIban, String toIban, double amount, Rail rail, String ExecutorID, String reason, double fee) {
        super(fromIban,amount,String.valueOf(rail)+" Transfer:"+ reason,"Transfer",ExecutorID);
        this.toIban = toIban;
        this.rail = rail;
        this.fee = fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getToIban(){ return toIban; }



    public Rail getRail(){ return rail; }




    public void setToIban(String toIban) {
        this.toIban = toIban;
    }

    public void setRail(Rail rail) {
        this.rail = rail;
    }


}