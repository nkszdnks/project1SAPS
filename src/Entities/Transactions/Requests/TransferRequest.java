package Entities.Transactions.Requests;

public class TransferRequest extends TransactionRequest {
    public enum Rail { LOCAL, SEPA, SWIFT }

    private String toIban;
    private Rail rail;

    public TransferRequest(String fromIban, String toIban, double amount, Rail rail, String ExecutorID, String reason) {
        super(fromIban,amount,String.valueOf(rail)+" Transfer:"+ reason,"Transfer",ExecutorID);
        this.toIban = toIban;
        this.rail = rail;
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