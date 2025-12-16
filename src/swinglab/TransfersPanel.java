package swinglab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class TransfersPanel extends JPanel implements ActionListener {

    private final JButton btnInterbank;     // Διατραπεζική μεταφορά
    private final JButton btnIntraBank;     // Ενδοτραπεζική μεταφορά
    private final JButton btnDeposit;       // Κατάθεση χρημάτων
    private final JButton btnWithdraw;      // Ανάληψη χρημάτων
    private final JButton btnStandingOrder;
    private final JButton btnClose;         // Κλείσιμο / Επιστροφή

    TransfersPanel() {
        setLayout(new BorderLayout(10,10));
        setBorder(new EmptyBorder(16,16,16,16));

        // ---- Title ----
        JLabel title = new JLabel("Transfers & Transactions", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        // ---- Center Buttons ----
        JPanel center = new JPanel(new GridLayout(2, 3, 12, 12));
        btnInterbank = new JButton("Interbank Transfer");     // Διατραπεζική μεταφορά
        btnIntraBank = new JButton("Intra-bank Transfer");    // Ενδοτραπεζική μεταφορά
        btnDeposit   = new JButton("Deposit Money");          // Κατάθεση χρημάτων
        btnWithdraw  = new JButton("Withdraw Money");         // Ανάληψη χρημάτων
        btnStandingOrder  = new JButton("Standing Transfer Order ");         // Ανάληψη χρημάτων

        
        center.add(btnStandingOrder);
        center.add(btnInterbank);
        center.add(btnIntraBank);
        center.add(btnDeposit);
        center.add(btnWithdraw);
        add(center, BorderLayout.CENTER);

        // ---- South (Close) ----
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnClose = new JButton("Close");
        btnClose.setPreferredSize(new Dimension(70, 26)); // small close button
        south.add(btnClose);
        add(south, BorderLayout.SOUTH);

        // ---- Event listeners ----
        btnInterbank.addActionListener(this);
        btnIntraBank.addActionListener(this);
        btnDeposit.addActionListener(this);
        btnStandingOrder.addActionListener(this);
        btnWithdraw.addActionListener(this);
        btnClose.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnClose) {
            // return to dashboard
            AppMediator.getCardLayout().show(AppMediator.getCards(), "dashboard");
        }
        else if (src == btnInterbank)
            AppMediator.getCardLayout().show(AppMediator.getCards(), "interbank");
        else if (src == btnIntraBank)
            AppMediator.getCardLayout().show(AppMediator.getCards(), "intrabank");
        else if (src == btnDeposit)
            AppMediator.getCardLayout().show(AppMediator.getCards(), "deposit");
        else if (src == btnWithdraw)
            AppMediator.getCardLayout().show(AppMediator.getCards(), "withdraw");
        else if (src == btnStandingOrder) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "standingTransferOrder");
        }
    }
}
