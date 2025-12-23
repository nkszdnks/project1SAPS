package Entities.Accounts;

import Entities.Accounts.Statements.Statement;
import Entities.Users.Business;

import java.time.LocalDate;
import java.util.ArrayList;

public class BusinessAcount extends BankAcount {
    private Business business;
    private double maintenanceFee;


    public BusinessAcount( String acountID,String IBAN, double interestRate, double accountBalance, LocalDate dateCreated,double maintainanceFee,double accruedInterest) {
        super(IBAN, acountID, interestRate, accountBalance, dateCreated,accruedInterest);
        this.maintenanceFee = maintainanceFee;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {

        this.business = business;
        this.setCustomer(business);
    }

    public double getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(float maintainanceFee) {
        this.maintenanceFee = maintainanceFee;
    }




    @Override
    public String marshal() {
        return "AccountID:" + getAcountID() +
                ",IBAN:" + getIBAN() +
                ",InterestRate:" + getInterestRate() +
                ",Balance:" + getAccountBalance() +
                ",DateCreated:" + getDateCreated() +
                ",AccountType:BUSINESS" +
                ",MainOwnerID:" + (business != null ? business.getUserId() : "") +
                ",SecondaryOwnerIDs:" +
                ",MaintenanceFee:" + maintenanceFee+
                ",accruedInterest:"+getAccruedInterest();
    }

    @Override
    public String getAccountType(){
        return "Business Account";
    }

}
