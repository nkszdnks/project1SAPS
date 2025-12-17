package Entities.AdminRequests;

import Entities.Accounts.BankAcount;
import Entities.Accounts.PersonalAccount;
import Entities.Transactions.Deposit;
import Entities.Transactions.TransactionStatus;
import Entities.Users.Customer;
import Entities.Users.IndividualPerson;
import Managers.AccountManager;
import Managers.TransactionManager;
import Managers.UserManager;

import java.time.LocalDateTime;

public class NewCoOwnerRequest extends AdminRequest {
    private PersonalAccount personalAccount;
    private IndividualPerson individualPerson;

    public NewCoOwnerRequest(Customer customer,PersonalAccount personalAccount,IndividualPerson individualPerson) {
        super(customer.getUserId()+"defaultID", "NewCoOwner", "New Co-Owner", customer);
        this.personalAccount = personalAccount;
        this.individualPerson = individualPerson;
    }

    public IndividualPerson getIndividualPerson() {
        return individualPerson;
    }

    public PersonalAccount getPersonalAccount() {
        return personalAccount;
    }

    @Override
    public void acceptRequest() {
        personalAccount.getSecondaryOwners().add(individualPerson);
    }

    @Override
    public void rejectRequest() {
        setRequestStatus(RequestStatus.REJECTED);
    }

    @Override
    public String marshal() {
        return "RequestType:Deposit,requestID:"+getRequestID()+",description:"+getDescription()+",customerUsername:"+getCustomer().getUsername()+",iban: "+",amount: "+",status:"+String.valueOf(getRequestStatus());
    }




}
