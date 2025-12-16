package Entities.Accounts;
import Entities.Users.IndividualPerson;

import java.time.LocalDate;
import java.util.ArrayList;

public class PersonalAccount extends BankAcount {
    private IndividualPerson mainOwner;//Maybe we can keep only the Customer from super class
    private ArrayList<IndividualPerson> secondaryOwners = new ArrayList<>();

    public PersonalAccount(String acountID, String IBAN, float interestRate, double accountBalance, LocalDate dateCreated) {
        super(IBAN,acountID,interestRate, accountBalance, dateCreated);
    }

    public IndividualPerson getMainOwner() {
        return mainOwner;
    }

    public void setSecondaryOwners(ArrayList<IndividualPerson> secondaryOwners) {
        this.secondaryOwners = secondaryOwners;
    }

    public void setMainOwner(IndividualPerson mainOwner) {
        this.mainOwner = mainOwner;
        this.setCustomer(mainOwner);
    }

    public ArrayList<IndividualPerson> getSecondaryOwners() {
        return secondaryOwners;
    }





    @Override
    public String marshal() {

        StringBuilder secOwners = new StringBuilder();
        for (IndividualPerson p : this.secondaryOwners) {
            secOwners.append(p.getUserId()).append("|");
        }

        return "AccountID:" + getAcountID() +
                ",IBAN:" + getIBAN() +
                ",InterestRate:" + getInterestRate() +
                ",Balance:" + getAccountBalance() +
                ",DateCreated:" + getDateCreated() +
                ",AccountType:PERSONAL" +
                ",MainOwnerID:" + (mainOwner != null ? mainOwner.getUserId() : "") +
                ",SecondaryOwnerIDs:" + secOwners +
                ",MaintenanceFee:";
    }

    @Override
    public String getAccountType() {
        return "Personal Account";
    }

}