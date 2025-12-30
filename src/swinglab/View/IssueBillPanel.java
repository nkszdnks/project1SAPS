package swinglab.View;

import Managers.BillManager;
import Managers.UserManager;
import Entities.Users.Business;
import Entities.Users.Customer;
import Entities.Users.User;
import Entities.Users.Bills;
import DataAccessObjects.BillsDAO;
import swinglab.View.AppMediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

public class IssueBillPanel extends JPanel implements ActionListener {

    private final JTextField billNumberField = new JTextField(15);
    private final JTextField amountField = new JTextField(10);
    private final JComboBox<String> rfComboBox = new JComboBox<>();
    private final JComboBox<String> customerComboBox = new JComboBox<>();
    private final JTextField descriptionField = new JTextField(20);
    private final JTextField dueDateField = new JTextField(10);
    private final JButton issueButton = new JButton("Issue Bill");
    private final JButton cancelButton = new JButton("Cancel");

    public IssueBillPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Issue New Bill", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Bill Number:"), gbc);
        gbc.gridx = 1;
        add(billNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("RF Code:"), gbc);
        gbc.gridx = 1;
        add(rfComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Amount (€):"), gbc);
        gbc.gridx = 1;
        add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Customer ID:"), gbc);
        gbc.gridx = 1;
        add(customerComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Due Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        dueDateField.setText(LocalDate.now().plusDays(30).toString());
        add(dueDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        add(descriptionField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(issueButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        issueButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }


    public void loadData() {
        rfComboBox.removeAllItems();
        customerComboBox.removeAllItems();

        Set<String> rfSet = new LinkedHashSet<>();
        Set<String> customerSet = new LinkedHashSet<>();

        BillsDAO dao = new BillsDAO("./data/bills/bills.csv");
        dao.loadBills(LocalDate.now());

        for (Bills b : dao.getIssued()) {
            rfSet.add(b.getRF());
            customerSet.add(b.getCustomer().getUserId());
        }
        for (Bills b : dao.getPaid()) {
            rfSet.add(b.getRF());
            customerSet.add(b.getCustomer().getUserId());
        }
        for (Bills b : dao.getExpired()) {
            rfSet.add(b.getRF());
            customerSet.add(b.getCustomer().getUserId());
        }

        for (String rf : rfSet) rfComboBox.addItem(rf);
        for (String id : customerSet) customerComboBox.addItem(id);

        if (rfComboBox.getItemCount() == 0) rfComboBox.addItem("RF10203040");
        if (customerComboBox.getItemCount() == 0) customerComboBox.addItem("TestID2");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueButton) {
            issueBill();
        } else if (e.getSource() == cancelButton) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "businessDashboard");
        }
    }

    private void issueBill() {
        try {
            User currentUser = AppMediator.getUser();
            if (!(currentUser instanceof Business)) {
                JOptionPane.showMessageDialog(this, "No business user logged in.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Business currentBusiness = (Business) currentUser;

            String billNumber = billNumberField.getText().trim();
            String rf = (String) rfComboBox.getSelectedItem();
            String customerID = (String) customerComboBox.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText().trim());
            LocalDate dueDate = LocalDate.parse(dueDateField.getText().trim());

            if (billNumber.isEmpty() || rf == null || customerID == null) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Customer customer = UserManager.getInstance().findCustomerByID(customerID);
            if (customer == null) {
                JOptionPane.showMessageDialog(this, "Customer with ID " + customerID + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (BillManager.getInstance().findBill(rf) != null) {
                JOptionPane.showMessageDialog(this, "RF code " + rf + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BillManager.getInstance().create(
                    rf,
                    billNumber,
                    (int) amount,
                    currentBusiness,
                    customer,
                    LocalDate.now(),
                    dueDate
            );

            JOptionPane.showMessageDialog(this, "Bill issued successfully to customer " + customerID, "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error issuing bill: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        billNumberField.setText("");
        rfComboBox.setSelectedIndex(0);
        customerComboBox.setSelectedIndex(0);
        amountField.setText("");
        descriptionField.setText("");
        dueDateField.setText(LocalDate.now().plusDays(30).toString());
    }
}