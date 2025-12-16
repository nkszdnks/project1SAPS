package Entities.Accounts;

import Entities.Accounts.Statements.Statement;
import Entities.Users.Business;
import Entities.Users.Customer;
import Entities.Users.IndividualPerson;
import Entities.Users.User;
import Managers.UserManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class BankAcount {
    private ArrayList<Statement> accountStatements = new ArrayList<>();
    private String IBAN;
    private String AcountID ;
    private float interestRate;
    private double accountBalance;
    private LocalDate dateCreated;
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BankAcount(String IBAN, String acountID, float interestRate, double accountBalance, LocalDate dateCreated) {
        this.IBAN = IBAN;
        this.AcountID = acountID;
        this.interestRate = interestRate;
        this.accountBalance = accountBalance;
        this.dateCreated = dateCreated;
    }

    public static BankAcount unmarshal(String csvLine) {

        String[] data = csvLine.split(",", -1);
        HashMap<String, String> map = new HashMap<>();

        // Build key-value map
        for (String c : data) {
            String[] kv = c.split(":", 2);
            if (kv.length == 2) {
                map.put(kv[0].trim(), kv[1].trim());
            }
        }

        String type = map.get("AccountType");

       return AccountFactory.CreateAccount(type,map);
    }




    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }





    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getAcountID() {
        return AcountID;
    }


    public float getInterestRate() {
        return interestRate;
    }

    public ArrayList<Statement> getAccountStatements() {
        return accountStatements;
    }

    public void setAccountStatements(ArrayList<Statement> accountStatements) {
        this.accountStatements = accountStatements;
    }

    public void setInterestRate(float loanInterestRate) {
        this.interestRate = loanInterestRate;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void addStatements(Statement accountStatement) {
        this.accountStatements.add(accountStatement);
    }

    public abstract String marshal();

    public abstract String getAccountType();


}
