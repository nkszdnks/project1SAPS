package swinglab;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class OpenAcountPanel extends JPanel {
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

    }
    public String getInitialDeposit() {
        return userField.getText();
    }

    public ArrayList<String> getCoOwnerAfms() {
        ArrayList<String> afms = new ArrayList<>();
        if (!coOwner1Field.getText().trim().isEmpty()) afms.add(coOwner1Field.getText().trim());
        if (!coOwner2Field.getText().trim().isEmpty()) afms.add(coOwner2Field.getText().trim());
        if (!coOwner3Field.getText().trim().isEmpty()) afms.add(coOwner3Field.getText().trim());
        return afms;
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void clearForm() {
        userField.setText("");
        coOwner1Field.setText("");
        coOwner2Field.setText("");
        coOwner3Field.setText("");
    }
    public void addFinishListener(ActionListener l) {
        btnLogin.addActionListener(l);
    }

    public void addClearListener(ActionListener l) {
        btnClear.addActionListener(l);
    }

    public void addAddCoOwnerListener(ActionListener l) {
        addCoOwner.addActionListener(l);
    }
    public void addNextCoOwnerField() {

        if (coOwners == 0) {
            c.gridx = 0; c.gridy = 1;
            add(new JLabel("Co-Owner 1 AFM:"), c);

            c.gridx = 1;
            add(coOwner1Field, c);

            coOwners++;
        }
        else if (coOwners == 1) {
            c.gridx = 0; c.gridy = 2;
            add(new JLabel("Co-Owner 2 AFM:"), c);

            c.gridx = 1;
            add(coOwner2Field, c);

            coOwners++;
        }
        else if (coOwners == 2) {
            c.gridx = 0; c.gridy = 3;
            add(new JLabel("Co-Owner 3 AFM:"), c);

            c.gridx = 1;
            add(coOwner3Field, c);

            coOwners++;
        }
        else {
            JOptionPane.showMessageDialog(
                    this,
                    "Maximum number of co-owners reached.",
                    "Limit reached",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        revalidate();
        repaint();
    }





//	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == btnLogin) {
//			String user = userField.getText().trim();
//            JOptionPane.showMessageDialog(this, "Request send! Waiting for admin to accept or reject.");
//
//		} else if (e.getSource() == btnClear) {
//			userField.setText("");userField.requestFocusInWindow();
//		}
//		else if (e.getSource() == addCoOwner) {
//			if(coOwners == 0) {
//				c.gridx = 0; c.gridy = 1; add(new JLabel("Co-Owner:"), c);
//				c.gridx = 1; c.gridy = 1; add(this.coOwner1Field, c);
//				this.coOwners++;
//			}
//			else if (coOwners == 1) {
//				c.gridx = 0; c.gridy = 2; add(new JLabel("Co-Owner2:"), c);
//				c.gridx = 1; c.gridy = 2; add(this.coOwner2Field, c);
//				this.coOwners++;
//			}
//			else if (coOwners == 2) {
//			c.gridx = 0; c.gridy = 3; add(new JLabel("Co-Owner3:"), c);
//			c.gridx = 1; c.gridy = 3; add(this.coOwner3Field, c);
//			this.coOwners++;
//			}
//			else {
//
//	                JOptionPane.showMessageDialog(this, "No more Co - Owners available", "Missing info", JOptionPane.WARNING_MESSAGE);
//	                return;
//
//			}
//			revalidate();
//		    repaint();
//
//		}
//	}
}

