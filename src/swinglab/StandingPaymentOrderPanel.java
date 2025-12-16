package swinglab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class StandingPaymentOrderPanel extends JPanel implements ActionListener {

    private final JTextField fromIban = new JTextField(22);
    private final JTextField rfCode = new JTextField(25);
    private final JTextField amount = new JTextField(10);
    private final JTextField description = new JTextField(20);
    private final JTextField startDate = new JTextField(10);
    private final JTextField frequency = new JTextField(20); // auto-set, not editable
    private final JButton btnFinish = new JButton("Create Standing Order");
    private final JButton btnHistory = new JButton("View Execution History");
    private final JButton btnClose = new JButton("Close");
    private final GridBagConstraints c = new GridBagConstraints();

    StandingPaymentOrderPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        // ---- Title ----
        JLabel title = new JLabel("Standing Payment Order", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        add(title, c);
        c.gridwidth = 1;

        // ---- Form Fields ----
        c.gridx=0; c.gridy=1; add(new JLabel("From IBAN:"), c);
        c.gridx=1; add(fromIban, c);

        c.gridx=0; c.gridy=2; add(new JLabel("RF Code:"), c);
        c.gridx=1; add(rfCode, c);

        c.gridx=0; c.gridy=3; add(new JLabel("Max Amount (€):"), c);
        c.gridx=1; add(amount, c);

        c.gridx=0; c.gridy=4; add(new JLabel("Description:"), c);
        c.gridx=1; add(description, c);

        c.gridx=0; c.gridy=5; add(new JLabel("Start Date (YYYY-MM-DD):"), c);
        c.gridx=1; add(startDate, c);

        // ---- Frequency (auto-filled) ----
        c.gridx=0; c.gridy=6; add(new JLabel("Frequency:"), c);
        frequency.setText("Auto: 2 days before account expiry");
        frequency.setEditable(false);
        frequency.setBackground(new Color(245,245,245));
        c.gridx=1; add(frequency, c);

        // ---- Buttons ----
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnHistory);
        buttons.add(btnClose);
        buttons.add(btnFinish);
        c.gridx=0; c.gridy=7; c.gridwidth=2;
        add(buttons, c);

        // ---- Events ----
        btnFinish.addActionListener(this);
        btnClose.addActionListener(this);
        btnHistory.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnFinish) {
            String iban = fromIban.getText().trim();
            String rf = rfCode.getText().trim();
            String amt = amount.getText().trim();

            if (iban.isEmpty() || rf.isEmpty() || amt.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please fill in IBAN, RF Code, and Amount.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                "Standing Payment Order created successfully!\n" +
                "IBAN: " + iban + "\nRF Code: " + rf + "\nAmount: " + amt + " €\n" +
                "Frequency: Auto 2 days before account expiry.\n\n" +
                "The system will execute this payment automatically.\n" +
                "After 3 failed attempts, it will log the failure but keep the order active.");
        }

        else if (src == btnHistory) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "standingPaymentHistory");
        }

        else if (src == btnClose) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "payments");
        }
    }
}
