package swinglab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

class OpenAcountPanel extends JPanel implements ActionListener {
    private final JTextField userField = new JTextField(14);
    private final JTextField coOwner1Field = new JTextField(14);
    private final JTextField coOwner2Field = new JTextField(14);
    private final JTextField coOwner3Field = new JTextField(14);
    private final JButton btnLogin = new JButton("Finish");
    private final JButton btnClear = new JButton("Clear");
    private final JButton addCoOwner = new JButton("Add Co-Owner");
    private final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private final GridBagConstraints c = new GridBagConstraints();
    private   int coOwners;

    // onSuccess switches to dashboard
    OpenAcountPanel() {
    	this.coOwners = 0;
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        setLayout(new GridBagLayout());
        c.insets = new Insets(10,10,10,10);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; add(new JLabel("Initial Deposit:"), c);
        c.gridx = 1; c.gridy = 0; add(userField, c);

       
        buttons.add(addCoOwner);
        buttons.add(btnClear);
        buttons.add(btnLogin);

        c.gridwidth = 2;
        c.gridx = 0; c.gridy = 4; add(buttons, c);

        // events
        addCoOwner.addActionListener(this);
        btnClear.addActionListener(this);
        btnLogin.addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			String user = userField.getText().trim();
            JOptionPane.showMessageDialog(this, "Request send! Waiting for admin to accept or reject.");
         
		} else if (e.getSource() == btnClear) {
			userField.setText("");userField.requestFocusInWindow();
		}
		else if (e.getSource() == addCoOwner) {
			if(coOwners == 0) {
				c.gridx = 0; c.gridy = 1; add(new JLabel("Co-Owner:"), c);
				c.gridx = 1; c.gridy = 1; add(this.coOwner1Field, c);
				this.coOwners++;
			}
			else if (coOwners == 1) {
				c.gridx = 0; c.gridy = 2; add(new JLabel("Co-Owner2:"), c);
				c.gridx = 1; c.gridy = 2; add(this.coOwner2Field, c);
				this.coOwners++;
			}
			else if (coOwners == 2) {
			c.gridx = 0; c.gridy = 3; add(new JLabel("Co-Owner3:"), c);
			c.gridx = 1; c.gridy = 3; add(this.coOwner3Field, c);
			this.coOwners++;
			}
			else {
				
	                JOptionPane.showMessageDialog(this, "No more Co - Owners available", "Missing info", JOptionPane.WARNING_MESSAGE);
	                return;
	            
			}
			revalidate();
		    repaint();

		}
	}
}

