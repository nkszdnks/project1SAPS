package Entities.Transactions.InterBankTransfers;

import Entities.Accounts.BankAcount;
import Entities.Accounts.Statements.Statement;
import Entities.Transactions.TransactionStatus;
import Managers.AccountManager;
import Managers.StatementManager;

import java.time.LocalDateTime;
import java.util.HashMap;


public class LocalExecutor implements TransferExecutor{
    @Override
    public ExecutionResult execute(ExecutionContext ctx) {
        HashMap<String,String> map = ctx.toLocal();
        String sourceIBAN = map.get("sourceIBAN");
        String targetIBAN = map.get("targetIBAN");
        double bankFee = Double.parseDouble(map.get("bankFee"));
        String transactionId = map.get("transactionId");
        double amount = Double.parseDouble(map.get("amount"));


        BankAcount source = AccountManager.getInstance().findAccountByIBAN(sourceIBAN);
        BankAcount target = AccountManager.getInstance().findAccountByIBAN(targetIBAN);
        if( target == null){
            return new ExecutionResult(false,"Destination Iban doesn't exist",transactionId);
        }
        if (source.getAccountBalance() - amount >= 0) {
            source.setAccountBalance(source.getAccountBalance() -amount - bankFee);
            target.setAccountBalance(target.getAccountBalance() + amount);
            double debited = amount + bankFee;
            return new ExecutionResult(true, "Transfer executed successfully: rail= LOCAL"
                    + " amount=" +  String.format("%.2f", amount)+
                    " fee=" + String.format("%.2f", bankFee)
                    + " debited=" + String.format("%.2f", debited),transactionId);
        }

        return new ExecutionResult(false,"Transfer failed insuffisient balance",transactionId);
    }




}
