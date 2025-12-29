package swinglab.Contollers;

import Entities.Transactions.Rails.WithdrawlRail;
import Entities.Transactions.Requests.WithdrawlRequest;
import swinglab.View.AppMediator;
import swinglab.View.WithdrawMoneyPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WithdrawlsController implements ActionListener {
    private static WithdrawlsController instance;
    private  WithdrawMoneyPanel view;

    public void setView(WithdrawMoneyPanel view) {
        this.view = view;
        view.addFinishListener(this);
        view.addCloseListener(this);
    }

    private WithdrawlsController() {

    }
    public static WithdrawlsController getInstance() {
        if(instance  == null){
            instance = new WithdrawlsController();
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
                handleWithdrawl();
                break;

            case "Close":
                AppMediator.getCardLayout().show(AppMediator.getCards(), "transfersPanel");
                break;
        }
    }



    private void handleWithdrawl() {

        try {
            String iban = view.getFromIban().trim();
            String stringAmount = view.getAmount().trim();

            if (iban.isEmpty() || String.valueOf(stringAmount).isEmpty() ) {
                throw new Exception("IBAN and Amount  is required.");
            }
            double amount = Double.parseDouble(stringAmount);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(view,
                        "Amount must be positive.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }


            var svc = new WithdrawlRail();

            var okLocal = new WithdrawlRequest(iban,amount,AppMediator.getUser().getUserId(),"Withdrawal");
            String message = svc.execute(okLocal);
            JOptionPane.showMessageDialog(view,
                    message,"Success",
                    JOptionPane.INFORMATION_MESSAGE);
            // For RF payments: amount is encoded in RF code or retrieved from backend


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(view,
                    ex.getMessage(),
                    "Withdrawal Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
