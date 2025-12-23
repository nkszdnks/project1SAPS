package swinglab.Contollers;

import Entities.Accounts.BankAcount;
import Entities.Users.Customer;
import Managers.AccountManager;
import swinglab.AppMediator;
import swinglab.hasIbanField;

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
        view.setIbans(myAccounts);
    }
}
