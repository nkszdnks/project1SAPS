package swinglab.Contollers;

import Entities.Accounts.BankAcount;
import Entities.Users.Customer;
import Entities.Users.IndividualPerson;
import Entities.Users.UserRole;
import Managers.AccountManager;
import swinglab.View.AppMediator;
import swinglab.View.hasIbanField;

import java.util.ArrayList;

public class JComboBoxController {
    private static JComboBoxController instance;
    private JComboBoxController(){
    }
    public static JComboBoxController getInstance(){
        if(instance == null){
            instance = new JComboBoxController();
            return instance;
        }
        return instance;
    }
    public void fillAccountsJComboBox(hasIbanField view){
        ArrayList<BankAcount> myAccounts = AccountManager.getInstance().getMyAccounts((Customer) AppMediator.getUser());
        if(((Customer)AppMediator.getUser()).getRole().equals(UserRole.PERSON)){
            myAccounts.addAll(AccountManager.getInstance().getMySecondaryAccounts((IndividualPerson) AppMediator.getUser()));
        }
        view.setIbans(myAccounts);
    }
}
