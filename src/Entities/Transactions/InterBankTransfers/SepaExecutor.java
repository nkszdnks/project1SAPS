package Entities.Transactions.InterBankTransfers;

import Entities.Accounts.BankAcount;
import Managers.AccountManager;

public class SepaExecutor implements TransferExecutor {



        private final BankTransferApiClient api;

        public SepaExecutor(BankTransferApiClient api) {
            this.api = api;
        }

        @Override
        public ExecutionResult execute(ExecutionContext ctx) {
            try {
                SepaTransferRequest req = ctx.toSepaRequest();
                var response = api.executeSepa(req);
                if(response.getString("status").equals("success")){
                    BankAcount source = AccountManager.getInstance().findAccountByIBAN(req.getSourceIBAN());
                    double bankFee = 2.3;
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
