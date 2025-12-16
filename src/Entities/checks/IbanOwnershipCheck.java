package Entities.checks;

import Entities.Transactions.Requests.TransactionRequest;
import Entities.Transactions.Requests.TransferRequest;
import Managers.AccountManager;

public class IbanOwnershipCheck extends BaseCheck {
    private boolean isValid(String iban){
        return iban != null && iban.startsWith("GR") && AccountManager.getInstance().findAccountByIBAN(iban) != null ;
    }
    @Override
    protected boolean apply(TransactionRequest req) {
        if(req.getType().equals("Transfer")) {
            return !(req.getFromIban().equals(((TransferRequest)req).getToIban())) && isValid(req.getFromIban()) && isValid(((TransferRequest)req).getToIban());
        }
        else {
            return isValid(req.getFromIban());
        }
    }
    @Override
    protected String failMessage() { return "Invalid IBAN format"; }
}
