package swinglab.View;

import Entities.Accounts.BankAcount;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class DepositMoneyPanel extends JPanel implements hasIbanField {
    public final JComboBox<String> fromIbans = new JComboBox<>();
    private final JTextField amountField = new JTextField(10);
    private final JTextField descriptionField = new JTextField(30);
    private final JButton btnFinish = new JButton("Finish");
    private final JButton btnClose = new JButton("Close");
    private final GridBagConstraints c = new GridBagConstraints();

    public DepositMoneyPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx=0; c.gridy=0; add(new JLabel("Account IBAN:"), c);
        c.gridx=1; add(fromIbans, c);
        c.gridx=0; c.gridy=1; add(new JLabel("Deposit Amount (€):"), c);
        c.gridx=1; add(amountField, c);
        c.gridx=0; c.gridy=2; add(new JLabel("Description:"), c);
        c.gridx=1; add(descriptionField, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnFinish);
        c.gridwidth=2; c.gridx=0; c.gridy=3;
        add(buttons, c);


    }


    public String getIbanField() {
        return String.valueOf(fromIbans.getSelectedItem());
    }

    public JButton getBtnFinish() {
        return btnFinish;
    }
    public JButton getBtnClose() {
        return btnClose;
    }
    public String getAmountField() {
        return amountField.getText();
    }
    public String getDescriptionField() {
        return descriptionField.getText();
    }
    // ----- Listener registration -----
    public void addFinishListener(java.awt.event.ActionListener l) {
        btnFinish.addActionListener(l);
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
