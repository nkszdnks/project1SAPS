package Entities.checks;

import Entities.Transactions.Requests.PaymentRequest;
import Entities.Transactions.Requests.TransactionRequest;
import Entities.Users.Bills;
import Managers.BillManager;
import Managers.UserManager;

public class RfCodeCheck extends BaseCheck {

    private String errorMessage = "";

    @Override
    protected boolean apply(TransactionRequest req) {

        PaymentRequest paymentReq = (PaymentRequest) req;
        String rfCode = paymentReq.getRfCode();

        // 1. RF code exists
        if (rfCode == null || rfCode.isBlank()) {
            errorMessage = "RF code is missing";
            return false;
        }

        // 2. RF format
        if (!rfCode.startsWith("RF")) {
            errorMessage = "Invalid RF code format";
            return false;
        }

        // 3. Bill exists
        Bills bill = BillManager.getInstance().findBill(rfCode);
        if (bill == null) {
            errorMessage = "RF code does not correspond to an existing bill";
            return false;
        }

        // 4. Bill has customer
        if (bill.getCustomer() == null) {
            errorMessage = "Bill is not associated with a customer";
            return false;
        }

        // 5. Executor is the bill owner
        if (!bill.getCustomer()
                .equals(UserManager.getInstance().findCustomerByID(req.getExecutorID()))) {
            errorMessage = "RF code does not belong to the executing customer";
            return false;
        }

        // 6. Set amount from bill
        paymentReq.setAmount(bill.getAmount());

        return true;
    }

    @Override
    protected String failMessage() {
        return errorMessage;
    }
}
