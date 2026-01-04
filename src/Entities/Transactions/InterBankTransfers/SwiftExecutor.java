package Entities.Transactions.InterBankTransfers;

import Entities.Accounts.BankAcount;
import Managers.AccountManager;
import org.json.JSONObject;

public class SwiftExecutor implements TransferExecutor {

    private final BankTransferApiClient api;

    public SwiftExecutor(BankTransferApiClient api) {
        this.api = api;
    }

    @Override
    public ExecutionResult execute(ExecutionContext ctx) {

        try {
            SwiftTransferRequest req = ctx.toSwiftRequest();
            JSONObject response = api.executeSwift(req);
            if(response.getString("status").equals("success")){
                BankAcount source = AccountManager.getInstance().findAccountByIBAN(req.getSourceIBAN());
                double bankFee = 3.7;
                source.setAccountBalance(source.getAccountBalance() - req.getAmount()- bankFee);
                double debited = req.getAmount() + bankFee;
                return new ExecutionResult(true, response.getString("message")
                        + " amount=" +  String.format("%.2f", req.getAmount())+
                        " fee=" + String.format("%.2f", bankFee)
                        + " debited=" + String.format("%.2f", debited),response.getString("transaction_id"));
            }



            return new ExecutionResult(response.getString("status").equals("success"),response.getString("message"),response.getString("transaction_id"));

        } catch (Exception e) {
            return new ExecutionResult(false,e.getMessage(),null);
        }
    }
}

