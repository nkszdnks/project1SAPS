package Managers;

import Commands.AccrueDailyInterestCommand;
import Commands.MaintenanceFeeCommand;
import Commands.PostMonthlyInterestCommand;
import DataAccessObjects.AcountsDAO;
import DataAccessObjects.FactoryDAO;
import DataAccessObjects.UsersDAO;
import Entities.Accounts.*;
import Entities.Transactions.Transfer;
import Entities.Users.Business;
import Entities.Users.Customer;
import Entities.Users.IndividualPerson;
import Entities.Transactions.TransactionStatus;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountManager implements Manager {
    private static  AccountManager INSTANCE;
    private FactoryDAO factoryDAO ;
    private AcountsDAO accountsDAO ;
    private List<BankAcount> bankAccounts = new ArrayList<>();

    private AccountManager() {
        factoryDAO = FactoryDAO.getInstance();
        accountsDAO = factoryDAO.getAcountsDAO();
    }

    public static AccountManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new AccountManager();
            return INSTANCE;
        }
        return INSTANCE;
    }

    public ArrayList<BankAcount> getBankAccounts() {
        return (ArrayList<BankAcount>) bankAccounts;
    }
    public ArrayList<BusinessAcount> getBusinessAccounts(){
        ArrayList<BusinessAcount> businessAccounts = new ArrayList<>();
        for (BankAcount account: bankAccounts) {
            if (account.getAccountType().equals("Business Account")) {
                businessAccounts.add((BusinessAcount) account);
            }
        }
        return businessAccounts;
    }
    public ArrayList<BankAcount> getMyAccounts(Customer owner) {
        ArrayList<BankAcount> myAccounts = new ArrayList<>();
        for(BankAcount account: bankAccounts){
            if(account.getCustomer() == null){
                continue;
            }
            if(account.getCustomer().equals(owner))
                myAccounts.add(account);
        }
        return myAccounts;
    }
    // Get all business accounts of a specific business
public ArrayList<BusinessAcount> getMyBusinessAccounts(Business b) {
    ArrayList<BusinessAcount> list = new ArrayList<>();
    for (BankAcount acc : bankAccounts) {
        if (acc instanceof BusinessAcount ba && acc.getCustomer().equals(b))
            list.add(ba);
    }
    return list;
}
    public ArrayList<BusinessAcount> getBusinessAccounts() {
        ArrayList<BusinessAcount> businessAccounts = new ArrayList<>();
        for(BankAcount account: bankAccounts){
            if(account instanceof BusinessAcount businessAccount){
                businessAccounts.add(businessAccount);
            }
        }
        return businessAccounts;
    }

    public void ComputeDailyInterests() {
        for (BankAcount account: bankAccounts) {
            AccrueDailyInterestCommand ac = new AccrueDailyInterestCommand(account);
            ac.execute();
        }

    }

    public void PostMonthlyInterests() {
        for (BankAcount account: bankAccounts) {
            PostMonthlyInterestCommand mc = new PostMonthlyInterestCommand(account);
            mc.execute();
        }

    }

    public void PostMaintenanceFee() {
        for (BusinessAcount account: getBusinessAccounts()) {
            MaintenanceFeeCommand mc = new MaintenanceFeeCommand(account);
            mc.execute();
        }

    }

public void TaxPay(String iban) {
        BankAcount acc = findAccountByIBAN(iban);
        if (!(acc instanceof BusinessAcount account)) {
            throw new IllegalArgumentException("IBAN does not belong to a Business Account");
        }

        float fee = account.getMaintenanceFee();
        if (fee <= 0) return; // τίποτα να χρεωθεί

        if (account.getAccountBalance() < fee) {
            throw new IllegalStateException("Insufficient balance for maintenance fee");
        }
         String txId = account.getIBAN();
        //Chang to the bank's IBAN
        String bankOfTucIBAN = "1234567890";

        Transfer maintenancePayment = new Transfer(
                txId,
                LocalDateTime.now(),
                fee,
                "Maintenance Tax Payment",
                account.getAcountID(),        // executor
                TransactionStatus.PENDING,    // αφήνουμε το Transact() να το περάσει COMPLETED
                account.getIBAN(),            // source
                bankOfTucIBAN,                // target
                0f,                           // bankFee
                "TRANSFER"
        );
        maintenancePayment.Transact();
    }

        
       
    public void TaxPayAllBusinesses() {
        for (BusinessAcount acc : getBusinessAccounts()) {
            if (acc instanceof BusinessAcount) {
                TaxPay(acc.getIBAN());          // ήδη υπάρχουσα μέθοδος
            }
        }
    }

    public void payInterest(){
        for(BankAcount acc : bankAccounts){
            double interestAmount = acc.getAccountBalance() * (acc.getInterestRate() / 100);
            acc.setAccountBalance(acc.getAccountBalance() + interestAmount);
        }
        for(BusinessAcount acc : getBusinessAccounts()){
            double interestAmount = acc.getAccountBalance() * (acc.getInterestRate() / 100);
            acc.setAccountBalance(acc.getAccountBalance() + interestAmount);
        }
    }

    public BankAcount findAccountByIBAN(String iban) {
        for (BankAcount bankAccount : bankAccounts) {
            if (bankAccount.getIBAN().equals(iban)) {
                return bankAccount;
            }
        }
        return null;
    }

    public BankAcount findAccountByOwner(Customer owner) {
        for (BankAcount bankAccount : bankAccounts) {
            if (bankAccount.getCustomer().equals(owner)) {
                return bankAccount;
            }
        }
        return null;
    }

    public ArrayList<PersonalAccount> getMySecondaryAccounts(IndividualPerson coOwner){
        ArrayList<PersonalAccount> secondaryAccounts = new ArrayList<>();
        for (BankAcount bankAccount : bankAccounts) {
            if(bankAccount.getAccountType().equals("PersonalAccount")){
                PersonalAccount account = (PersonalAccount) bankAccount;
                for(IndividualPerson owner2: account.getSecondaryOwners()){
                    if(owner2.equals(coOwner)){
                        secondaryAccounts.add(account);
                    }
                }
            }
        }
        return secondaryAccounts;
    }

    @Override
    public void restore() {
        bankAccounts = accountsDAO.loadAccounts();    }

    @Override
    public void save() {
      accountsDAO.saveAccounts(bankAccounts);
    }

}