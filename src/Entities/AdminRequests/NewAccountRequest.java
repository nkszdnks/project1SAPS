package Entities.AdminRequests;

import Entities.Accounts.PersonalAccount;
import Entities.Users.Customer;
import Entities.Users.IndividualPerson;
import Managers.AccountManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class NewAccountRequest extends AdminRequest{

    private static final Random random = new Random();
    private PersonalAccount personalAccount;
        private double initialDeposit;
        private ArrayList<IndividualPerson> coOwners = new ArrayList<IndividualPerson>();


        public NewAccountRequest(Customer customer, double initialDeposit, ArrayList<IndividualPerson> coOwners ) {
            super(customer.getUserId()+"defaultID"+String.valueOf(random.nextInt(1000)), "NewAccount", "New Account", customer);
            this.initialDeposit = initialDeposit;
            this.coOwners = coOwners;

        }



        public PersonalAccount getPersonalAccount() {
            return personalAccount;
        }

        @Override
        public void acceptRequest() {
            personalAccount = CreateNewAccount();
            personalAccount.setMainOwner((IndividualPerson) getCustomer());
            personalAccount.setSecondaryOwners(coOwners);
            personalAccount.setAccountBalance(initialDeposit);
            AccountManager.getInstance().getBankAccounts().add(personalAccount);
            setRequestStatus(RequestStatus.ACCEPTED);
        }

        @Override
        public void rejectRequest() {
            setRequestStatus(RequestStatus.REJECTED);
        }

        @Override
        public String marshal() {
            StringBuilder secOwners = new StringBuilder();
            for (IndividualPerson p : coOwners) {
                secOwners.append(p.getAfm()).append("|");
            }
            return "RequestType:NewAccount,requestID:"+getRequestID()+",description:"+getDescription()+",customerUsername:"+getCustomer().getUsername()+",status:"+String.valueOf(getRequestStatus())+",coOwnersAfm:"+secOwners+",initialDeposit:"+String.valueOf(initialDeposit);
        }
        private PersonalAccount CreateNewAccount(){
             String iban = generateIBAN("100");
             String accountID = generateAccountID();
             return new PersonalAccount(accountID,iban,1,0.0, LocalDate.now());
        }

    private static String generateIBAN(String accountType) {
        StringBuilder sb = new StringBuilder();

        sb.append("GR");          // Country
        sb.append(accountType);   // Account type

        for (int i = 0; i < 7; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
    private static String generateAccountID() {
        StringBuilder sb = new StringBuilder();

        sb.append("ACC");          // Country

        for (int i = 0; i < 5; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public double getInitialDeposit() {
        return initialDeposit;
    }

    public ArrayList<IndividualPerson> getCoOwners() {
        return coOwners;
    }
}

