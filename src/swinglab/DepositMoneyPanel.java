package swinglab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class DepositMoneyPanel extends JPanel implements ActionListener {
    private final JTextField ibanField = new JTextField(20);
    private final JTextField amountField = new JTextField(10);
    private final JButton btnFinish = new JButton("Finish");
    private final JButton btnClose = new JButton("Close");
    private final GridBagConstraints c = new GridBagConstraints();

    DepositMoneyPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx=0; c.gridy=0; add(new JLabel("Account IBAN:"), c);
        c.gridx=1; add(ibanField, c);
        c.gridx=0; c.gridy=1; add(new JLabel("Deposit Amount (€):"), c);
        c.gridx=1; add(amountField, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnFinish);
        c.gridwidth=2; c.gridx=0; c.gridy=2;
        add(buttons, c);

        btnFinish.addActionListener(this);
        btnClose.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnFinish){
            JOptionPane.showMessageDialog(this,
                "Deposit request sent for approval by Administrator.");

        } else if(e.getSource()==btnClose){
            AppMediator.getCardLayout().show(AppMediator.getCards(), "transfersPanel");
        }
    }
}
