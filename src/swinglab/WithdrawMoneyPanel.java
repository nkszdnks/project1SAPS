package swinglab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WithdrawMoneyPanel extends JPanel {
    private final JTextField ibanField = new JTextField(20);
    private final JTextField amountField = new JTextField(10);
    private final JButton btnFinish = new JButton("Finish");
    private final JButton btnClose = new JButton("Close");
    private final GridBagConstraints c = new GridBagConstraints();

    WithdrawMoneyPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx=0; c.gridy=0; add(new JLabel("Account IBAN:"), c);
        c.gridx=1; add(ibanField, c);
        c.gridx=0; c.gridy=1; add(new JLabel("Withdrawal Amount (€):"), c);
        c.gridx=1; add(amountField, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnFinish);
        c.gridwidth=2; c.gridx=0; c.gridy=2;
        add(buttons, c);


    }


    // ----- Getters for Controller -----

    public String getFromIban() { return ibanField.getText(); }
    public String getAmount() { return amountField.getText(); }
    // ----- Listener registration -----
    public void addCloseListener(java.awt.event.ActionListener l) {
        btnClose.addActionListener(l);
    }

    public void addFinishListener(java.awt.event.ActionListener l) {
        btnFinish.addActionListener(l);
    }
}
