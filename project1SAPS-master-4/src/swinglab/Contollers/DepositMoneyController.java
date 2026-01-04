package swinglab.Contollers;

import Entities.Accounts.BankAcount;
import Entities.AdminRequests.DepositAdminRequest;
import Entities.Transactions.Rails.DepositRail;
import Entities.Users.Customer;
import Managers.AccountManager;
import swinglab.View.AppMediator;
import swinglab.View.DepositMoneyPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepositMoneyController implements ActionListener {
    private static DepositMoneyController instance;
    private DepositMoneyPanel view;

    public void setView(DepositMoneyPanel view) {
        this.view = view;
        view.addFinishListener(this);
        view.addCloseListener(this);
    }

    private DepositMoneyController() {

    }
    public static DepositMoneyController getInstance() {
        if(instance  == null){
            instance = new DepositMoneyController();
            return instance;
        }
        return instance;
    }
    public void setAccounts(){
        JComboBoxController.getInstance().fillAccountsJComboBox(view);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {
            case "Finish":
                handleDeposit();
                break;

            case "Close":
                AppMediator.getCardLayout().show(AppMediator.getCards(), "transfersPanel");
                break;
        }
    }

    private void handleDeposit() {

        try {
            String iban = view.getIbanField();
            String amount = view.getAmountField();
            String description = view.getDescriptionField();


            if (iban.isEmpty() || amount.isEmpty()) {
                throw new Exception("IBAN and amount are required.");
            }

            BankAcount b = AccountManager.getInstance().findAccountByIBAN(iban);


            var svc = new DepositRail();

            var okLocal = new DepositAdminRequest(description,(Customer) AppMediator.getUser(),b,Double.parseDouble(amount));
            String message = svc.execute(okLocal);
            JOptionPane.showMessageDialog(view,
                    message,"Success",
                    JOptionPane.INFORMATION_MESSAGE);
            // For RF payments: amount is encoded in RF code or retrieved from backend


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(view,
                    ex.getMessage(),
                    "Deposit Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
