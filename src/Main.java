import DataAccessObjects.AcountsDAO;
import DataAccessObjects.FactoryDAO;
import DataAccessObjects.UsersDAO;
import Entities.Accounts.BankAcount;
import Entities.Accounts.BusinessAcount;
import Entities.Accounts.PersonalAccount;
import Entities.Users.User;
import Entities.Users.IndividualPerson;
import Entities.Users.Business;
import Entities.Users.Admin;
import Managers.BillManager;
import Managers.UserManager;

import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {


        UserManager.getInstance().restore();

        List<User> users = UserManager.getInstance().getUsers();

        System.out.println("\n=== USERS LOADED ===");

        for (User u : users) {

            System.out.println("-----------------------------");
            System.out.println("UserID     : " + u.getUserId());
            System.out.println("Username   : " + u.getUsername());
            System.out.println("Role       : " + u.getRole());

            // Identify subclass
            if (u instanceof IndividualPerson) {
                IndividualPerson p = (IndividualPerson) u;
                System.out.println("Type       : Physical Person");
                System.out.println("First Name : " + p.getFirstName());
                System.out.println("Last Name  : " + p.getLastName());
                System.out.println("AFM        : " + p.getAfm());
                System.out.println("VAT        : " + p.getVAT());
            }
            else if (u instanceof Business) {
                Business b = (Business) u;
                System.out.println("Type       : Business");
                System.out.println("Name       : " + b.getBusinessName());
                System.out.println("AFM        : " + b.getAfm());
                System.out.println("IBAN       : " + b.getCorporateAcount());
            }
            else if (u instanceof Admin) {
                Admin a = (Admin) u;
                System.out.println("Type       : Admin");
                System.out.println("EmployeeID : " + a.getEmployeeId());
            }

        }
        IndividualPerson t = new IndividualPerson("123","nzidanakis","haha","gay@gmail.com","8999","ika","kai","738283838","90");
        UserManager.getInstance().registerUser(t);

        System.out.println("-----------------------------");
        System.out.println("Total users loaded: " + users.size());

        UserManager.getInstance().save();

        List<BankAcount> accounts = FactoryDAO.getInstance().getAcountsDAO().loadAccounts();
        System.out.println("\n=== Loaded Accounts: " + accounts.size() + " ===\n");

        // ----------------------------------------------------
        // 4. Print all loaded accounts
        // ----------------------------------------------------
        for (BankAcount a : accounts) {
            System.out.println("---------------------------------------------");
            System.out.println("AccountID: " + a.getAcountID());
            System.out.println("IBAN     : " + a.getIBAN());
            System.out.println("Balance  : " + a.getAccountBalance());
            System.out.println("Interest : " + a.getInterestRate());
            System.out.println("Created  : " + a.getDateCreated());

            if (a instanceof PersonalAccount pa) {
                System.out.println("TYPE     : PERSONAL");
                System.out.println("Main Owner: " +
                        (pa.getMainOwner() == null ? "null" : pa.getMainOwner()));

                if (pa.getSecondaryOwners().isEmpty()) {
                    System.out.println("Real object : none");
                } else {
                    pa.getSecondaryOwners().forEach(
                            u -> System.out.print(u.getUserId() + " ")
                    );
                    System.out.println();
                }
            }

            else if (a instanceof BusinessAcount ba) {
                System.out.println("TYPE     : BUSINESS");
                System.out.println("Business Owner: " +
                        (ba.getBusiness() == null ? "null" : ba.getBusiness().getUserId()));
                System.out.println("Maintenance Fee: " + ba.getMaintenanceFee());
            }
        }
        Random rand = new Random();
        //PersonalAccount test = new PersonalAccount("AC00"+String.valueOf(rand.nextInt(100)),"GR020030303"+String.valueOf(rand.nextInt(100)),1,33.22, LocalDate.EPOCH);
        //test.getSecondaryOwners().add((IndividualPerson)users.getFirst());
        //accounts.add(test);
        FactoryDAO.getInstance().getAcountsDAO().saveAccounts(accounts);
        BillManager.getInstance().restore();
        BillManager.getInstance().save();
    }
}
