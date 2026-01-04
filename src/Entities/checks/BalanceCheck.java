package Entities.checks;


import Entities.Transactions.Requests.TransactionRequest;
import Managers.AccountManager;

public class BalanceCheck extends BaseCheck {
    @Override
    protected boolean apply(TransactionRequest req) {

        return req.getAmount() <= AccountManager.getInstance().findAccountByIBAN(req.getFromIban()).getAccountBalance();
    }
    @Override
    protected String failMessage() { return "Insufficient balance"; }
}