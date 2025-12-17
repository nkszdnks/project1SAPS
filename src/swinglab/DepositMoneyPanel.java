package swinglab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DepositMoneyPanel extends JPanel {
    private final JTextField ibanField = new JTextField(20);
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
        c.gridx=1; add(ibanField, c);
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


    public JTextField getIbanField() {
        return ibanField;
    }

    public JButton getBtnFinish() {
        return btnFinish;
    }
    public JButton getBtnClose() {
        return btnClose;
    }
    public JTextField getAmountField() {
        return amountField;
    }
    public JTextField getDescriptionField() {
        return descriptionField;
    }
    // ----- Listener registration -----
    public void addFinishListener(java.awt.event.ActionListener l) {
        btnFinish.addActionListener(l);
    }

    public void addCloseListener(java.awt.event.ActionListener l) {
        btnClose.addActionListener(l);
    }
}
