package Entities.checks;


import Entities.Transactions.Requests.PaymentRequest;
import Entities.Transactions.Requests.TransactionRequest;
import Entities.Transactions.TransactionStatus;
import Entities.Users.BillStatus;
import Entities.Users.Bills;
import Managers.BillManager;
import Managers.UserManager;

public class RfCodeCheck extends BaseCheck {
    private StringBuilder errorMessage;
    private boolean isValid(String RfCode){
        Bills bill = BillManager.getInstance().findBill(RfCode);
        return RfCode != null && RfCode.startsWith("RF") && bill != null && bill.getCustomer() != null ;
    }
    @Override
    protected boolean apply(TransactionRequest req) {
        String rf = ((PaymentRequest)req).getRfCode();
        if(isValid(((PaymentRequest)req).getRfCode())){
            ((PaymentRequest)req).setAmount(BillManager.getInstance().findBill(rf).getAmount());
        }

        return isValid(((PaymentRequest)req).getRfCode()) && BillManager.getInstance().findBill(((PaymentRequest) req).getRfCode()).getCustomer().equals(UserManager.getInstance().findCustomerByID(req.getExecutorID())) ;
    }

@Override
protected String failMessage() { return "Invalid RF code:"; }
}
