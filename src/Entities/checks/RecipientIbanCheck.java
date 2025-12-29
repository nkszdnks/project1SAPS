package Entities.checks;

import Entities.Transactions.Requests.TransactionRequest;
import Entities.Transactions.Requests.TransferRequest;
import Managers.AccountManager;

public class RecipientIbanCheck extends BaseCheck{
    private String errorMessage = "";

    @Override
    protected boolean apply(TransactionRequest req) {
        TransferRequest transferRequest = (TransferRequest) req;
        if(transferRequest.getRail().equals(TransferRequest.Rail.LOCAL)) {
            if (AccountManager.getInstance().findAccountByIBAN(transferRequest.getToIban()) == null) {
                errorMessage = "Recipient account does not exist";
                return false;
            }
        }
        return true;
    }

    @Override
    protected String failMessage() {
        return errorMessage;
    }
}
