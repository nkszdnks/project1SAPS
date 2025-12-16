package Entities.Users;


import Entities.StandingOrders.*;
import Entities.Users.BillStatus;
import Entities.Users.Business;
import Entities.Users.Customer;
import Managers.UserManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class Bills  {
    private BillStatus status;
    private String RF;
    public static ArrayList<String> RFsUsed = new ArrayList<>();
    private String billNumber;
    private double amount;
    private Business issuer;
    private Customer customer;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public Bills(String RF, String billNumber, double amount, Business issuer, Customer customer, LocalDate issueDate, LocalDate dueDate,BillStatus status) {
        this.RF = RF;
        this.billNumber = billNumber;
        this.amount = amount;
        this.issuer = issuer;
        this.customer = customer;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        RFsUsed.add(RF);
        this.status = status;
    }

    public Bills(){}

    public String  getRF() {
        return RF;
    }

    public void setRF(String RF) {
        this.RF = RF;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Business getIssuer() {
        return issuer;
    }



    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }



    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    public String marshal() {
        return String.valueOf(
                "billNumber:" + getBillNumber() +
                        ",RF:" + getRF() +
                        ",issuerVat:" + (getIssuer() != null ? getIssuer().getVAT() : "null") +
                        ",customerVat:" + (getCustomer() != null ? getCustomer().getVAT() : "null") +
                        ",amount:" + getAmount() +
                        ",issueDate:" + getIssueDate() +
                        ",dueDate:" + getDueDate()+
                        ",status:" + getStatus()
        );    }

    public static Bills unmarshal(String csvLine) {

        String[] data = csvLine.split(",", -1);
        HashMap<String, String> map = new HashMap<>();

        for (String c : data) {
            String[] kv = c.split(":", 2);
            if (kv.length == 2) map.put(kv[0], kv[1]);
        }

        BillStatus status = BillStatus.valueOf(map.get("status"));
        String RF = map.get("RF");
        String billNumber = map.get("billNumber");
        double amount = Double.parseDouble(map.get("amount"));
        LocalDate issueDate = LocalDate.parse(map.get("issueDate"));
        LocalDate dueDate = LocalDate.parse(map.get("dueDate"));
        String  issuerVat = map.get("issuerVat");
        String  customerVat = map.get("customerVat");
        Customer customer = UserManager.getInstance().findCustomerByVAT(customerVat);
        Business issuer = (Business)UserManager.getInstance().findCustomerByVAT(issuerVat);

        return new Bills(RF,billNumber,amount,issuer,customer,issueDate,dueDate,status);

    }
}
