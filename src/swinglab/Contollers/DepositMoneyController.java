package swinglab.Contollers;

import Entities.Accounts.BankAcount;
import Entities.AdminRequests.DepositAdminRequest;
import Entities.Transactions.Rails.DepositRail;
import Entities.Transactions.Rails.PaymentRail;
import Entities.Transactions.Requests.PaymentRequest;
import Entities.Users.Customer;
import Managers.AccountManager;
import Managers.BillManager;
import Managers.UserManager;
import swinglab.AppMediator;
import swinglab.DepositMoneyPanel;
import swinglab.PayBillsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepositMoneyController implements ActionListener {

    private final DepositMoneyPanel view;

    public DepositMoneyController(DepositMoneyPanel view) {
        this.view = view;

        // Fill combo box

        // Listeners
        view.addFinishListener(this);
        view.addCloseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {
            case "Finish":
                handleDeposit();
                break;

            case "Close":
                AppMediator.getCardLayout().show(AppMediator.getCards(), "payments");
                break;
        }
    }

    private void handleDeposit() {

        try {
            String iban = view.getIbanField().getText().trim();
            String amount = view.getAmountField().getText().trim();
            String description = view.getDescriptionField().getText().trim();


            if (iban.isEmpty() || amount.isEmpty()) {
                throw new Exception("IBAN and amount are required.");
            }

            BankAcount b = AccountManager.getInstance().findAccountByIBAN(iban);

            if(b == null) {
                throw new Exception("This IBAN does not exist.");
            }

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
