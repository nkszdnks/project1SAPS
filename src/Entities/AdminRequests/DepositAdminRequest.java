package Entities.AdminRequests;

import Entities.Accounts.BankAcount;
import Entities.Transactions.Requests.TransactionRequest;
import Entities.Users.Admin;
import Entities.Users.Customer;

public class DepositAdminRequest extends AdminRequest {
    private BankAcount bankAccount;
    private double amount;
    public DepositAdminRequest(String requestID, String requestType, String description, Customer customer, Admin admin,BankAcount bankAccount, double amount) {
        super(requestID, requestType, "DEPOSIT:"+description, customer, admin);
        this.bankAccount = bankAccount;
        this.amount = amount;
    }


}
