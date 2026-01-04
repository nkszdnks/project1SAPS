package swinglab.View;
import Entities.Accounts.BankAcount;
import Entities.Accounts.PersonalAccount;
import swinglab.Contollers.CoOwnersController;
import swinglab.Contollers.TransactionHistoryController;

import java.awt.*;

import javax.swing.*;;


public class AccountDetailsDialog extends JDialog {
    AccountDetailsDialog(Window owner, String iban, String type, String balance, String interest, BankAcount b) {
        super(owner, "Account Details", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.anchor = GridBagConstraints.WEST;

        // Row 0
        c.gridx = 0; c.gridy = 0; content.add(new JLabel("IBAN:"), c);
        c.gridx = 1; c.gridy = 0; content.add(new JTextField(iban, 22) {{
            setEditable(false);
        }}, c);

        // Row 1
        c.gridx = 0; c.gridy = 1; content.add(new JLabel("Main-Owner:"), c);
        c.gridx = 1; c.gridy = 1; content.add(new JTextField(type, 22) {{
            setEditable(false);
        }}, c);

        // Row 2
        c.gridx = 0; c.gridy = 2; content.add(new JLabel("Balance (€):"), c);
        c.gridx = 1; c.gridy = 2; content.add(new JTextField(balance, 22) {{
            setEditable(false);
        }}, c);
        
        c.gridx = 0; c.gridy = 3; content.add(new JLabel("Interest from bank:"), c);
        c.gridx = 1; c.gridy = 3; content.add(new JTextField(interest, 22) {{
            setEditable(false);
        }}, c);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton close = new JButton("Close");
        buttons.add(close);
        JButton co_owners = new JButton("Co-owners");
        buttons.add(co_owners);
        if(b.getAccountType().equals("Business Account")){
            co_owners.setVisible(false);
        }
        
        JButton transactions = new JButton("Transaction History");
        buttons.add(transactions);
        add(buttons, BorderLayout.SOUTH);

        c.gridwidth = 2;
        c.gridx = 0; c.gridy = 4; c.anchor = GridBagConstraints.EAST;
        content.add(buttons, c);

        setContentPane(content);
        pack();
        setLocationRelativeTo(owner);

        close.addActionListener(e -> dispose());
        if(b.getAccountType().equals("Personal Account")) {
            CoOwnersController.getInstance().setModel((PersonalAccount) b);
            co_owners.addActionListener(e -> AppMediator.getCardLayout().show(AppMediator.getCards(), "coOwners") );
        }
        TransactionHistoryController.getInstance().setModel(b);
        transactions.addActionListener(e ->AppMediator.getCardLayout().show(AppMediator.getCards(), "transactionsPanel") );

    }
}

