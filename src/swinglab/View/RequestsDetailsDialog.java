package swinglab.View;

import Entities.Accounts.BankAcount;
import Entities.Accounts.PersonalAccount;
import Entities.AdminRequests.*;
import Entities.Users.IndividualPerson;
import swinglab.AppMediator;
import swinglab.Contollers.CoOwnersController;
import swinglab.Contollers.TransactionHistoryController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class RequestsDetailsDialog extends JDialog implements ActionListener {
    private final NumberFormat euroFormat =
            NumberFormat.getCurrencyInstance(Locale.GERMANY);
    private JButton close,acceptbtn,rejectbtn;
    private AdminRequest adminRequest;
    RequestsDetailsDialog(Window owner, String requestID, String type, String customerName, String status, AdminRequest adminRequest) {
        super(owner, "Request Details", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.adminRequest = adminRequest;

        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.anchor = GridBagConstraints.WEST;

        // Row 0
        c.gridx = 0; c.gridy = 0; content.add(new JLabel("Request ID:"), c);
        c.gridx = 1; c.gridy = 0; content.add(new JTextField(requestID, 22) {{
            setEditable(false);
        }}, c);

        // Row 1
        c.gridx = 0; c.gridy = 1; content.add(new JLabel("Customer's Name:"), c);
        c.gridx = 1; c.gridy = 1; content.add(new JTextField(customerName, 22) {{
            setEditable(false);
        }}, c);

        // Row 2
        c.gridx = 0; c.gridy = 2; content.add(new JLabel("Type:"), c);
        c.gridx = 1; c.gridy = 2; content.add(new JTextField(type, 22) {{
            setEditable(false);
        }}, c);

        c.gridx = 0; c.gridy = 3; content.add(new JLabel("Status:"), c);
        c.gridx = 1; c.gridy = 3; content.add(new JTextField(status, 22) {{
            setEditable(false);
        }}, c);
        c.gridx = 0; c.gridy = 4; content.add(new JLabel("Description:"), c);
        c.gridx = 1; c.gridy = 4; content.add(new JTextField(adminRequest.getDescription(), 22) {{
            setEditable(false);
        }}, c);

        // Buttons

        if(adminRequest.getRequestType().equals("Deposit")){
            // Row 2
            c.gridx = 0; c.gridy = 5; content.add(new JLabel("Amount:"), c);
            c.gridx = 1; c.gridy = 5; content.add(new JTextField(euroFormat.format(((DepositAdminRequest)adminRequest).getAmount()), 22) {{
                setEditable(false);
            }}, c);

            c.gridx = 0; c.gridy = 6; content.add(new JLabel("Iban:"), c);
            c.gridx = 1; c.gridy = 6; content.add(new JTextField(((DepositAdminRequest)adminRequest).getBankAccount().getIBAN(), 22) {{
                setEditable(false);
            }}, c);

        }
        else if(adminRequest.getRequestType().equals("NewCoOwner")){
            // Row 2
            NewCoOwnerRequest newCoOwnerRequest = (NewCoOwnerRequest)adminRequest;
            c.gridx = 0; c.gridy = 5; content.add(new JLabel("New Co-Owner's Full Name:"), c);
            c.gridx = 1; c.gridy = 5; content.add(new JTextField(newCoOwnerRequest.getIndividualPerson().getFirstName()+newCoOwnerRequest.getIndividualPerson().getLastName(), 22) {{
                setEditable(false);
            }}, c);

            c.gridx = 0; c.gridy = 6; content.add(new JLabel("Email:"), c);
            c.gridx = 1; c.gridy = 6; content.add(new JTextField(newCoOwnerRequest.getIndividualPerson().getEmail(), 22) {{
                setEditable(false);
            }}, c);
            c.gridx = 0; c.gridy = 7; content.add(new JLabel("Afm:"), c);
            c.gridx = 1; c.gridy = 7; content.add(new JTextField(newCoOwnerRequest.getIndividualPerson().getAfm(), 22) {{
                setEditable(false);
            }}, c);
            c.gridx = 0; c.gridy = 8; content.add(new JLabel("Phone:"), c);
            c.gridx = 1; c.gridy = 8; content.add(new JTextField(newCoOwnerRequest.getIndividualPerson().getPhoneNumber(), 22) {{
                setEditable(false);
            }}, c);
            c.gridx = 0; c.gridy = 9; content.add(new JLabel("Iban:"), c);
            c.gridx = 1; c.gridy = 9; content.add(new JTextField(newCoOwnerRequest.getPersonalAccount().getIBAN(), 22) {{
                setEditable(false);
            }}, c);

        }
        else if(adminRequest.getRequestType().equals("NewAccount")){
            // Row 2
            NewAccountRequest newAccountRequest = (NewAccountRequest)adminRequest;
            c.gridx = 0; c.gridy = 5; content.add(new JLabel("Initial Deposit:"), c);
            c.gridx = 1; c.gridy = 5; content.add(new JTextField(String.valueOf(newAccountRequest.getInitialDeposit()), 22) {{
                setEditable(false);
            }}, c);
            int i = 0;
            for(IndividualPerson coOwner:newAccountRequest.getCoOwners()) {

                c.gridx = 0;
                c.gridy = i+6;
                content.add(new JLabel("Co Owner's "+String.valueOf(i+1)+" Afm:"), c);
                c.gridx = 1;
                c.gridy =i+6;
                content.add(new JTextField(coOwner.getAfm(), 22) {{
                    setEditable(false);
                }}, c);
                i++;


            }


        }
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        close = new JButton("Close");
        buttons.add(close);
        acceptbtn = new JButton("Accept");
        buttons.add(acceptbtn);
        rejectbtn = new JButton("Reject");
        buttons.add(rejectbtn);



        add(buttons, BorderLayout.SOUTH);

        c.gridwidth = 2;
        c.gridx = 0; c.gridy = 10; c.anchor = GridBagConstraints.EAST;
        content.add(buttons, c);
        rejectbtn.addActionListener(this);
        acceptbtn.addActionListener(this);
        setContentPane(content);
        pack();
        setLocationRelativeTo(owner);

        close.addActionListener(e -> dispose());
        if(adminRequest.getRequestStatus().equals(RequestStatus.REJECTED)||adminRequest.getRequestStatus().equals(RequestStatus.ACCEPTED)){
            rejectbtn.setVisible(false);
            acceptbtn.setVisible(false);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == acceptbtn){
            adminRequest.acceptRequest();
            JOptionPane.showMessageDialog(this,
                    "Request accepted","Success",
                    JOptionPane.INFORMATION_MESSAGE);
            acceptbtn.setVisible(false);
            rejectbtn.setVisible(false);
        }
        else if(e.getSource() == rejectbtn){
            adminRequest.rejectRequest();
            JOptionPane.showMessageDialog(this,
                    "Request rejected","Success",
                    JOptionPane.INFORMATION_MESSAGE);
            acceptbtn.setVisible(false);
            rejectbtn.setVisible(false);
        }
    }
}
