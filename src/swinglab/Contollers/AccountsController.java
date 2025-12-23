package swinglab.Contollers;

import Entities.Accounts.BankAcount;
import Entities.Users.Customer;
import Managers.AccountManager;
import swinglab.AccountsPanel;
import swinglab.AppMediator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AccountsController extends WindowAdapter implements ActionListener {
    private static AccountsController instance;
    private  AccountsPanel view;
    private  Customer model;


    public static AccountsController getInstance() {
        if(instance == null){
            instance = new AccountsController();
            return instance;
        }
        return instance;
    }


    public void setModel(Customer model) {
        this.model = model;
        if(model != null) {
            loadAccounts();
        }

    }

    private AccountsController() {

        // Load table data initially
    }

    public void setView(AccountsPanel view) {
        this.view = view;

        // Attach button listeners
        view.details.addActionListener(this);
        view.clearSel.addActionListener(this);
        view.closePan.addActionListener(this);
        view.newAcount.addActionListener(this);
    }




    private void loadAccounts() {
        view.clearTable();

        for (BankAcount account : AccountManager.getInstance().getMyAccounts(model)) {
            view.addAccountRow(
                    account.getIBAN(),
                    account.getCustomer().getFullName(),
                    account.getAccountBalance()
            );
        }
        view.newAcount.setEnabled(true);
    }
    public void loadAllAccounts() {
        view.clearTable();

        for (BankAcount account : AccountManager.getInstance().getBankAccounts()) {
            view.addAccountRow(
                    account.getIBAN(),
                    account.getCustomer()!=null?account.getCustomer().getFullName():"BankOfTUC",
                    account.getAccountBalance()
            );
        }
        view.newAcount.setEnabled(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {

            case "View Details":
                view.showSelectedRowDetails();
                break;

            case "Clear Selection":
                view.clearSelection();
                break;

            case "Close":
                if(model==null){
                    AppMediator.getCardLayout().show(AppMediator.getCards(),"adminDashboard");
                    break;
                }
                AppMediator.getCardLayout().show(AppMediator.getCards(), "dashboard");
                break;

            case "Open New Acount":
                AppMediator.getCardLayout().show(AppMediator.getCards(), "newAcount");
                break;


            default:
                System.out.println("Unhandled action: " + cmd);
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Window closed.");
        System.exit(0);
    }
}
