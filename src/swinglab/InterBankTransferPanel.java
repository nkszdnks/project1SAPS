package swinglab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class InterBankTransferPanel extends JPanel implements ActionListener {
    private final JTextField fromIban = new JTextField(20);
    private final JTextField toIban = new JTextField(20);
    private final JTextField amount = new JTextField(10);
    private final JTextField reason = new JTextField(20);
    private final JButton btnFinish = new JButton("Finish");
    private final JButton btnClose = new JButton("Close");
    private final GridBagConstraints c = new GridBagConstraints();

    InterBankTransferPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx=0; c.gridy=0; add(new JLabel("From IBAN:"), c);
        c.gridx=1; add(fromIban, c);
        c.gridx=0; c.gridy=1; add(new JLabel("To IBAN (other bank):"), c);
        c.gridx=1; add(toIban, c);
        c.gridx=0; c.gridy=2; add(new JLabel("Amount (€):"), c);
        c.gridx=1; add(amount, c);
        c.gridx=0; c.gridy=3; add(new JLabel("Reason:"), c);
        c.gridx=1; add(reason, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnFinish);
        c.gridwidth=2;
        c.gridx=0; c.gridy=4;
        add(buttons, c);

        btnFinish.addActionListener(this);
        btnClose.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnFinish){
            JOptionPane.showMessageDialog(this,
                "Interbank transfer submitted.\nA 0.5€ fee will be applied.\nAwaiting confirmation from remote bank.");
        } else if(e.getSource()==btnClose){
            AppMediator.getCardLayout().show(AppMediator.getCards(), "transfersPanel");
        }
    }
}
