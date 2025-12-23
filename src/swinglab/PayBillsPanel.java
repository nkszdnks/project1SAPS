package swinglab;

import Entities.Accounts.BankAcount;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PayBillsPanel extends JPanel implements hasIbanField{

    private final JComboBox<String> fromIbans = new JComboBox<>();
    private final JTextField rfCode = new JTextField(25);
    private final JComboBox<String> businessList = new JComboBox<>();

    private final JButton btnPay = new JButton("Pay Bill");
    private final JButton btnClose = new JButton("Close");

    private final GridBagConstraints c = new GridBagConstraints();

    public PayBillsPanel() {

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("Pay Bills (RF Code)", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        add(title, c);
        c.gridwidth = 1;

        // Form fields
        c.gridx=0; c.gridy=1; add(new JLabel("From IBAN:"), c);
        c.gridx=1; add(fromIbans, c);

        c.gridx=0; c.gridy=2; add(new JLabel("RF Code:"), c);
        c.gridx=1; add(rfCode, c);

        c.gridx=0; c.gridy=3; add(new JLabel("Business:"), c);
        c.gridx=1; add(businessList, c);


        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnPay);

        c.gridx = 0; c.gridy = 5; c.gridwidth = 2;
        add(buttons, c);
    }

    // ----- Getters for Controller -----

    public String getFromIban() { return String.valueOf(fromIbans.getSelectedItem()); }
    public String getRfCode() { return rfCode.getText(); }

    public String getSelectedBusiness() {
        return (String) businessList.getSelectedItem();
    }

    // ----- Allow Controller to set business options -----
    public void setBusinesses(java.util.List<String> businesses) {
        businessList.removeAllItems();
        for (String b : businesses)
            businessList.addItem(b);
    }

    // ----- Listener registration -----
    public void addPayListener(java.awt.event.ActionListener l) {
        btnPay.addActionListener(l);
    }

    public void addCloseListener(java.awt.event.ActionListener l) {
        btnClose.addActionListener(l);
    }

    @Override
    public void setIbans(ArrayList<BankAcount> accounts) {
        fromIbans.removeAllItems();
        for (BankAcount b : accounts)
            fromIbans.addItem(b.getIBAN());
    }
}
