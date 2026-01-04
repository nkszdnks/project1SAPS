package swinglab.View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ChangePasswordPanel extends JPanel implements ActionListener {

    private final JPasswordField oldPass = new JPasswordField(16);
    private final JPasswordField newPass = new JPasswordField(16);
    private final JPasswordField confirmPass = new JPasswordField(16);
    private final JButton btnFinish = new JButton("Change Password");
    private final JButton btnClose = new JButton("Close");
    private final GridBagConstraints c = new GridBagConstraints();

    ChangePasswordPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Change Password", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        add(title, c);
        c.gridwidth = 1;

        // --- Input fields ---
        c.gridx=0; c.gridy=1; add(new JLabel("Current Password:"), c);
        c.gridx=1; add(oldPass, c);

        c.gridx=0; c.gridy=2; add(new JLabel("New Password:"), c);
        c.gridx=1; add(newPass, c);

        c.gridx=0; c.gridy=3; add(new JLabel("Confirm New Password:"), c);
        c.gridx=1; add(confirmPass, c);

        // --- Buttons ---
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnFinish);

        c.gridx=0; c.gridy=4; c.gridwidth=2;
        add(buttons, c);

        btnFinish.addActionListener(this);
        btnClose.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnFinish) {
            String oldPwd = new String(oldPass.getPassword());
            String newPwd = new String(newPass.getPassword());
            String confirm = new String(confirmPass.getPassword());

            if (oldPwd.isEmpty() || newPwd.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!newPwd.equals(confirm)) {
                JOptionPane.showMessageDialog(this,
                    "New passwords do not match.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newPwd.length() < 6) {
                JOptionPane.showMessageDialog(this,
                    "Password must be at least 6 characters long.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // TODO: integrate with AppMediator / Bank backend to actually update password
            JOptionPane.showMessageDialog(this,
                "Password changed successfully!\nYou will need to use the new password next time you log in.");
        }
        else if (e.getSource() == btnClose) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "dashboard");
        }
    }
}
