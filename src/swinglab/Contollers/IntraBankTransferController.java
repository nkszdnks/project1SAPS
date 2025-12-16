package swinglab.Contollers;

import Entities.Transactions.Rails.TransferRail;
import Entities.Transactions.Requests.TransferRequest;
import swinglab.AppMediator;
import swinglab.IntraBankTransferPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Entities.Transactions.Requests.TransferRequest.Rail.LOCAL;

public class IntraBankTransferController implements ActionListener {

    private final IntraBankTransferPanel view;

    public IntraBankTransferController(IntraBankTransferPanel view) {
        this.view = view;

        // Controller takes over event handling
        view.btnFinish.addActionListener(this);
        view.btnClose.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == view.btnFinish) {
            handleFinish();
        }

        if (e.getSource() == view.btnClose) {
            AppMediator.getCardLayout().show(
                    AppMediator.getCards(),
                    "transfersPanel"
            );
        }
    }

    private void handleFinish() {
        try {
            String from = view.fromIban.getText().trim();
            String to = view.toIban.getText().trim();
            String reason = view.reason.getText().trim();
            double amt = Double.parseDouble(view.amount.getText().trim());

            if (from.isEmpty() || to.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                        "IBAN fields cannot be empty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (amt <= 0) {
                JOptionPane.showMessageDialog(view,
                        "Amount must be positive.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            var svc = new TransferRail();

            var okLocal = new TransferRequest(from,to,amt, LOCAL,AppMediator.getUser().getUserId(), reason);
            String message = svc.execute(okLocal);
            JOptionPane.showMessageDialog(view,
                    message);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view,
                    "Invalid amount format.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
