package Entities.Transactions.Requests;

public class WithdrawlRequest extends TransactionRequest {


    public  WithdrawlRequest(String fromIban, double amount, String ExecutorID, String reason) {
        super(fromIban,amount,reason,"Withdrawl",ExecutorID);
    }


}
