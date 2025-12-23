package Entities.AdminRequests;

import Entities.Accounts.BankAcount;
import Entities.Transactions.Deposit;
import Entities.Transactions.Requests.TransactionRequest;
import Entities.Transactions.TransactionStatus;
import Entities.Users.Admin;
import Entities.Users.Customer;
import Managers.TransactionManager;
import swinglab.AppMediator;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

public class DepositAdminRequest extends AdminRequest {
    private static final Random random = new Random();
    private BankAcount bankAccount;
    private double amount;

    public double getAmount() {
        return amount;
    }

    @Override
    public void acceptRequest() {
        Deposit deposit = new Deposit("defaultID",AppMediator.getToday().atTime(LocalTime.now()),getAmount(),getDescription(),getCustomer().getUserId(), TransactionStatus.PENDING,getBankAccount());
        TransactionManager.getInstance().Transact(deposit);
        setRequestStatus(RequestStatus.ACCEPTED);
    }

    @Override
    public void rejectRequest() {
        setRequestStatus(RequestStatus.REJECTED);
    }

    @Override
    public String marshal() {
        return "RequestType:Deposit,requestID:"+getRequestID()+",description:"+getDescription()+",customerUsername:"+getCustomer().getUsername()+",iban:"+getBankAccount().getIBAN()+",amount:"+String.valueOf(getAmount())+",status:"+String.valueOf(getRequestStatus());
    }

    public DepositAdminRequest(String description, Customer customer, BankAcount bankAccount, double amount) {
        super(String.format(
                "NAID-%s-%04d"+
                        LocalDateTime.now().toLocalDate().toString().replace("-", "")+
                        customer.getUserId()+String.valueOf(random.nextInt(1000))), "Deposit", description, customer);
        this.bankAccount = bankAccount;
        this.amount = amount;
    }

    public BankAcount getBankAccount() {
        return bankAccount;
    }
}
