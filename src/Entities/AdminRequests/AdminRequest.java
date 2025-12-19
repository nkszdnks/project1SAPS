package Entities.AdminRequests;

import Entities.Accounts.AccountFactory;
import Entities.Accounts.BankAcount;
import Entities.Accounts.PersonalAccount;
import Entities.Users.*;
import Managers.AccountManager;
import Managers.UserManager;
import com.sun.net.httpserver.Request;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AdminRequest {
    private String requestID;
    private String requestType;
    private Customer customer;
    private Admin admin;
    private String description;
    private RequestStatus requestStatus;

    public String getRequestType() {
        return requestType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public AdminRequest(String requestID, String requestType, String description, Customer customer) {
        this.requestID = requestID;
        this.requestType = requestType;
        this.description = description;
        this.customer = customer;
        requestStatus = RequestStatus.PENDING;
    }

    public String getDescription() {
        return description;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }
    protected void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
    public abstract void acceptRequest();
    public abstract void rejectRequest();
    public static AdminRequest unmarshal(String csvLine) {

        String[] data = csvLine.split(",", -1);
        HashMap<String, String> map = new HashMap<>();

        // Build key-value map
        for (String c : data) {
            String[] kv = c.split(":", 2);
            if (kv.length == 2) {
                map.put(kv[0].trim(), kv[1].trim());
            }
        }

        String type = map.get("RequestType");

        switch (type) {
            case "Deposit":
                Customer customer = (Customer)UserManager.getInstance().findUser(map.get("customerUsername"));
                BankAcount bankAcount = AccountManager.getInstance().findAccountByIBAN(map.get("iban"));
                DepositAdminRequest depositAdminRequest = new DepositAdminRequest(
                        map.get("description"),
                        customer,
                        bankAcount,
                        Double.parseDouble(map.get("amount"))
                );
                depositAdminRequest.setRequestStatus(RequestStatus.valueOf(map.get("status")));
                return depositAdminRequest;


            case "NewCoOwner":
                Customer adder = (Customer)UserManager.getInstance().findUser(map.get("customerUsername"));
                PersonalAccount personalAccount = (PersonalAccount) AccountManager.getInstance().findAccountByIBAN(map.get("iban"));
                IndividualPerson newCoOwner = UserManager.getInstance().findUserByFullName(map.get("coOwnerFullName"));
                NewCoOwnerRequest newCoOwnerRequest = new NewCoOwnerRequest(
                        adder,
                        personalAccount,
                        newCoOwner
                );
                newCoOwnerRequest.setRequestStatus(RequestStatus.valueOf(map.get("status")));
                return newCoOwnerRequest;

            case "NewAccount":
                Customer ad = (Customer)UserManager.getInstance().findUser(map.get("customerUsername"));
                ArrayList<IndividualPerson> newCoOwners = new ArrayList<>();
                // Secondary Owners (split by "|")
                String secStr = map.get("coOwnersAfm");
                String[] temp = secStr.split("\\|", -1);
                for (String coOwnerAfm : temp) {
                    IndividualPerson newCoOwnersObj = UserManager.getInstance().findUserByAfm(coOwnerAfm);
                    if (newCoOwnersObj != null) {
                        newCoOwners.add(newCoOwnersObj);
                    }
                    }
                NewAccountRequest newAccountRequest = new NewAccountRequest(
                        ad,
                        Double.parseDouble(map.get("initialDeposit")),
                        newCoOwners
                );
                newAccountRequest.setRequestStatus(RequestStatus.valueOf(map.get("status")));
                return newAccountRequest;

            default:
                throw new RuntimeException("Unknown role in CSV: " + type);
        }

    }
    public abstract String marshal();        // to CSV

}
