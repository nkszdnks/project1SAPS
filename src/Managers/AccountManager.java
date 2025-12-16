package Managers;

import DataAccessObjects.AcountsDAO;
import DataAccessObjects.FactoryDAO;
import DataAccessObjects.UsersDAO;
import Entities.Accounts.*;
import Entities.Transactions.Transfer;
import Entities.Users.Business;
import Entities.Users.Customer;
import Entities.Users.IndividualPerson;


import java.time.LocalDate;
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

    public void AssignInterest(String iban , int interest) {
        BankAcount bankAccount = findAccountByIBAN(iban);
        bankAccount.setInterestRate(interest);

    }

//    public void TaxPay(String iban) {
//        BusinessAcount account = (BusinessAcount) findAccountByIBAN(iban);
//        float amount = account.getMaintenanceFee();
//        TransactionManager transactionManager = TransactionManager.getInstance();
//        transactionManager.Transact(new Transfer(Company.getUserOfTuc(), new BusinessAcount[]{account, BusinessAccount.getBankOfTuc()},"Maintenance Tax Payment" , amount , LocalDate.now()));
//    }

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