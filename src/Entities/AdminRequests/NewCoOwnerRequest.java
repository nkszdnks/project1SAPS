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
import java.util.Random;


public class NewCoOwnerRequest extends AdminRequest {
    private static final Random random = new Random();
    private  PersonalAccount personalAccount;
    private IndividualPerson individualPerson;

    public NewCoOwnerRequest(Customer customer,PersonalAccount personalAccount,IndividualPerson individualPerson) {
        super(String.format(
                "NAID-%s-%04d"+
                        LocalDateTime.now().toLocalDate().toString().replace("-", "")+
                        customer.getUserId()+String.valueOf(random.nextInt(1000))), "NewCoOwner", "New Co-Owner", customer);
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
        setRequestStatus(RequestStatus.ACCEPTED);
    }

    @Override
    public void rejectRequest() {
        setRequestStatus(RequestStatus.REJECTED);
    }

    @Override
    public String marshal() {
        return "RequestType:NewCoOwner,requestID:"+getRequestID()+",description:"+getDescription()+",customerUsername:"+getCustomer().getUsername()+",iban:"+getPersonalAccount().getIBAN()+",status:"+String.valueOf(getRequestStatus())+",coOwnerFullName:"+getIndividualPerson().getFirstName()+getIndividualPerson().getLastName();
    }




}
