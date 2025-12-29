package swinglab.View;

import Entities.Users.Customer;
import swinglab.Contollers.PayBillsController;
import swinglab.Contollers.StandingTransferOrderController;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class PaymentsPanel extends JPanel implements ActionListener {

    private final JButton btnPayBill;            // Πληρωμή λογαριασμών
    private final JButton btnStandingPayment;    // Πάγια πληρωμή λογαριασμών
    private final JButton btnClose;              // Κλείσιμο / Επιστροφή

    PaymentsPanel() {
        setLayout(new BorderLayout(10,10));
        setBorder(new EmptyBorder(16,16,16,16));

        // ---- Title ----
        JLabel title = new JLabel("Payments & Standing Orders", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        // ---- Center Buttons ----
        JPanel center = new JPanel(new GridLayout(1, 2, 12, 12));
        btnPayBill = new JButton("Pay Bills (RF Code)");
        btnStandingPayment = new JButton("Standing Payment Order");

        center.add(btnPayBill);
        center.add(btnStandingPayment);
        add(center, BorderLayout.CENTER);

        // ---- South (Close) ----
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnClose = new JButton("Close");
        btnClose.setPreferredSize(new Dimension(70, 26));
        south.add(btnClose);
        add(south, BorderLayout.SOUTH);

        // ---- Event Listeners ----
        btnPayBill.addActionListener(this);
        btnStandingPayment.addActionListener(this);
        btnClose.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnClose) {
            AppMediator.goToHomeDashboard();

        }
        else if (src == btnPayBill) {
            PayBillsController.getInstance().setAccounts();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "payBills");
        }
        else if (src == btnStandingPayment) {
            StandingTransferOrderController.getInstance().setModel((Customer) AppMediator.getUser());
            AppMediator.getCardLayout().show(AppMediator.getCards(), "standingPaymentOrder");
        }
    }
}
