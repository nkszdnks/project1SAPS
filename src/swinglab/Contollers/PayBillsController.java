package swinglab.Contollers;

import Entities.Transactions.Rails.PaymentRail;
import Entities.Transactions.Requests.PaymentRequest;
import Managers.BillManager;
import Managers.UserManager;
import swinglab.AppMediator;
import swinglab.PayBillsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayBillsController implements ActionListener {

    private final PayBillsPanel view;

    public PayBillsController(PayBillsPanel view) {
        this.view = view;

        // Fill combo box
        view.setBusinesses(UserManager.getInstance().getBusinessesNames());

        // Listeners
        view.addPayListener(this);
        view.addCloseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {
            case "Pay Bill":
                handlePayment();
                break;

            case "Close":
                AppMediator.getCardLayout().show(AppMediator.getCards(), "payments");
                break;
        }
    }

    private void handlePayment() {

        try {
            String iban = view.getFromIban().trim();
            String rf = view.getRfCode().trim();
            String business = view.getSelectedBusiness();

            if (iban.isEmpty() || rf.isEmpty()) {
                throw new Exception("IBAN and RF Code are required.");
            }

            if (business == null) {
                throw new Exception("Please select a business.");
            }
            if(!BillManager.getInstance().findBill(rf).getIssuer().getBusinessName().equals(business)) {
                throw new Exception("Business issue and RF code does not match.");
            }

            var svc = new PaymentRail();

            var okLocal = new PaymentRequest(iban,rf,AppMediator.getUser().getUserId());
            String message = svc.execute(okLocal);
            JOptionPane.showMessageDialog(view,
                    message,"Success",
                    JOptionPane.INFORMATION_MESSAGE);
            // For RF payments: amount is encoded in RF code or retrieved from backend


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(view,
                    ex.getMessage(),
                    "Payment Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
