package swinglab.View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewCoOwnerPanel extends JPanel {

    private final JTextField coOwnerName = new JTextField(20);
    private final JTextField coOwnerAFM = new JTextField(12);
    private final JTextField coOwnerEmail = new JTextField(25);
    private final JTextField coOwnerPhone = new JTextField(15);
    private final JButton btnSend = new JButton("Send Request");
    private final JButton btnClose = new JButton("Close");

    public NewCoOwnerPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Add New Co-Owner Request");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        c.gridx=0; c.gridy=0; c.gridwidth=2;
        add(title, c);
        c.gridwidth=1;



        c.gridx=0; c.gridy=1; add(new JLabel("Co-Owner Full Name:"), c);
        c.gridx=1; add(coOwnerName, c);

        c.gridx=0; c.gridy=2; add(new JLabel("Co-Owner Tax ID (AFM):"), c);
        c.gridx=1; add(coOwnerAFM, c);

        c.gridx=0; c.gridy=3; add(new JLabel("Email:"), c);
        c.gridx=1; add(coOwnerEmail, c);

        c.gridx=0; c.gridy=4; add(new JLabel("Phone:"), c);
        c.gridx=1; add(coOwnerPhone, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnSend);

        c.gridx=0; c.gridy=5; c.gridwidth=2;
        add(buttons, c);
    }

    /* ---------- getters ---------- */

    public String getName() { return coOwnerName.getText().trim(); }
    public String getAfm() { return coOwnerAFM.getText().trim(); }
    public String getEmail() { return coOwnerEmail.getText().trim(); }
    public String getPhone() { return coOwnerPhone.getText().trim(); }

    public void addSendListener(ActionListener l) {
        btnSend.addActionListener(l);
    }

    public void addCloseListener(ActionListener l) {
        btnClose.addActionListener(l);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.WARNING_MESSAGE);
    }

    public void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
