package Entities.checks;

import Entities.Transactions.Requests.TransactionRequest;
import Entities.Transactions.Requests.TransferRequest;
import Managers.AccountManager;

public class IbanFormatCheck extends BaseCheck {
    private boolean isValid(String iban){
        return iban != null && iban.startsWith("GR") && AccountManager.getInstance().findAccountByIBAN(iban) != null ;
    }
    private boolean isOwned(String iban,String ExecutorID){
        return AccountManager.getInstance().findAccountByIBAN(iban).getCustomer().getUserId().equals(ExecutorID) ;
    }
    @Override
    protected boolean apply(TransactionRequest req) {
        if(req.getType().equals("Transfer")) {
            return !(req.getFromIban().equals(((TransferRequest)req).getToIban())) && isValid(req.getFromIban()) && isOwned(req.getFromIban(), req.getExecutorID());
        }
        else {
            return isValid(req.getFromIban()) && isOwned(req.getFromIban(), req.getExecutorID()) ;
        }
    }
    @Override
    protected String failMessage() { return "Invalid IBAN "; }
}