package Entities.Accounts;

import Entities.Users.Business;
import Entities.Users.IndividualPerson;
import Entities.Users.User;
import Managers.UserManager;

import java.time.LocalDate;
import java.util.HashMap;

public class AccountFactory {
    public static BankAcount CreateAccount(String type, HashMap<String,String> map){
        if ("PERSONAL".equals(type)) {

            PersonalAccount p = new PersonalAccount(
                    map.get("AccountID"),
                    map.get("IBAN"),
                    Float.parseFloat(map.getOrDefault("InterestRate", "0")),
                    Double.parseDouble(map.getOrDefault("Balance", "0")),
                    LocalDate.parse(map.get("DateCreated")),
                    Double.parseDouble(map.getOrDefault("accruedInterest", "0"))
            );


            p.setMainOwner((IndividualPerson) UserManager.getInstance().findCustomerByID(map.get("MainOwnerID")));

            // Secondary Owners (split by "|")
            String secStr = map.get("SecondaryOwnerIDs");
            String[] temp = secStr.split("\\|", -1);
            for (String ownerId : temp) {

                User owner = UserManager.getInstance().findCustomerByID(ownerId);

                if (owner != null) {
                    p.getSecondaryOwners().add((IndividualPerson) owner);
                }
            }




            return p;
        }

        // =========================
        // BUSINESS ACCOUNT
        // =========================
        if ("BUSINESS".equals(type)) {

            BusinessAcount b = new BusinessAcount(
                    map.get("AccountID"),
                    map.get("IBAN"),
                    Float.parseFloat(map.get("InterestRate")),
                    Double.parseDouble(map.get("Balance")),
                    LocalDate.parse(map.get("DateCreated")),
                    Float.parseFloat(map.get("MaintenanceFee")),
                    Double.parseDouble(map.getOrDefault("accruedInterest", "0"))
            );

            // Business Owner (same field as main owner)
            b.setBusiness((Business) UserManager.getInstance().findCustomerByID(map.get("MainOwnerID")));
            ((Business) UserManager.getInstance().findCustomerByID(map.get("MainOwnerID"))).setCorporateAcount(b);



            return b;
        }

        throw new IllegalArgumentException("Unknown AccountType: " + type);
    }

}
