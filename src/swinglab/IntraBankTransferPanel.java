package swinglab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IntraBankTransferPanel extends JPanel {
    public final JTextField fromIban = new JTextField(20);
    public final JTextField toIban = new JTextField(20);
    public final JTextField amount = new JTextField(10);
    public final JTextField reason = new JTextField(20);
    public final JButton btnFinish = new JButton("Finish");
    public final JButton btnClose = new JButton("Close");
    protected final GridBagConstraints c = new GridBagConstraints();

    IntraBankTransferPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx=0; c.gridy=0; add(new JLabel("From IBAN:"), c);
        c.gridx=1; add(fromIban, c);
        c.gridx=0; c.gridy=1; add(new JLabel("To IBAN (same bank):"), c);
        c.gridx=1; add(toIban, c);
        c.gridx=0; c.gridy=2; add(new JLabel("Amount (€):"), c);
        c.gridx=1; add(amount, c);
        c.gridx=0; c.gridy=3; add(new JLabel("Reason:"), c);
        c.gridx=1; add(reason, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnFinish);
        c.gridwidth=2; c.gridx=0; c.gridy=4;
        add(buttons, c);


    }


}
