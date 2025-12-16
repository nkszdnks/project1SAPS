package swinglab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class NewCoOwnerPanel extends JPanel implements ActionListener {

    private final JTextField accountIban = new JTextField(22);
    private final JTextField coOwnerName = new JTextField(20);
    private final JTextField coOwnerAFM = new JTextField(12);
    private final JTextField coOwnerEmail = new JTextField(25);
    private final JTextField coOwnerPhone = new JTextField(15);
    private final JButton btnSend = new JButton("Send Request");
    private final JButton btnClose = new JButton("Close");
    private final GridBagConstraints c = new GridBagConstraints();

    NewCoOwnerPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        // ---- Title ----
        JLabel title = new JLabel("Add New Co-Owner Request", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        add(title, c);
        c.gridwidth = 1;

        // ---- Form Fields ----
        c.gridx=0; c.gridy=1; add(new JLabel("Account IBAN:"), c);
        c.gridx=1; add(accountIban, c);

        c.gridx=0; c.gridy=2; add(new JLabel("Co-Owner Full Name:"), c);
        c.gridx=1; add(coOwnerName, c);

        c.gridx=0; c.gridy=3; add(new JLabel("Co-Owner Tax ID (AFM):"), c);
        c.gridx=1; add(coOwnerAFM, c);

        c.gridx=0; c.gridy=4; add(new JLabel("Email:"), c);
        c.gridx=1; add(coOwnerEmail, c);

        c.gridx=0; c.gridy=5; add(new JLabel("Phone:"), c);
        c.gridx=1; add(coOwnerPhone, c);

        // ---- Buttons ----
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnSend);

        c.gridx=0; c.gridy=6; c.gridwidth=2;
        add(buttons, c);

        btnSend.addActionListener(this);
        btnClose.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSend) {
            String iban = accountIban.getText().trim();
            String name = coOwnerName.getText().trim();

            if (iban.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please fill in at least IBAN and Co-Owner Name.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                "Request to add " + name + " as co-owner for account " + iban + " sent!\n" +
                "The Administrator will review and approve or reject the request.");
        } 
        else if (e.getSource() == btnClose) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "coOwners");
        }
    }
}
