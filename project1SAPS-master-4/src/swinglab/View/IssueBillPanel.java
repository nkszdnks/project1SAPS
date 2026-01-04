package swinglab.View;

import DataAccessObjects.FactoryDAO;
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
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class IssueBillPanel extends JPanel implements ActionListener {

    private final JTextField billNumberField = new JTextField(15);
    private final JTextField amountField = new JTextField(10);
    private final JTextField rfField = new JTextField(15);
    private final JTextField customerField = new JTextField(15);
    private final JComboBox<Integer> startYear = new JComboBox<>();
    private final JComboBox<Integer> startMonth = new JComboBox<>();
    private final JComboBox<Integer> startDay = new JComboBox<>();
    private final JComboBox<Integer> dueYear = new JComboBox<>();
    private final JComboBox<Integer> dueMonth = new JComboBox<>();
    private final JComboBox<Integer> dueDay = new JComboBox<>();
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
        add(rfField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Amount (€):"), gbc);
        gbc.gridx = 1;
        add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Customer ID:"), gbc);
        gbc.gridx = 1;
        add(customerField, gbc);

        populateDateBoxes(startYear, startMonth, startDay);
        addDateRow(5, gbc, "Start Date:", startYear, startMonth, startDay);

        populateDateBoxes(dueYear, dueMonth, dueDay);
        addDateRow(6, gbc, "Due Date:", dueYear, dueMonth, dueDay);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(issueButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        issueButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    public LocalDate getStartDate() {
        return LocalDate.of(
                (Integer) startYear.getSelectedItem(),
                (Integer) startMonth.getSelectedItem(),
                (Integer) startDay.getSelectedItem()
        );
    }

    public LocalDate getDueDate() {
        return LocalDate.of(
                (Integer) dueYear.getSelectedItem(),
                (Integer) dueMonth.getSelectedItem(),
                (Integer) dueDay.getSelectedItem()
        );
    }

    private void addDateRow(int y, GridBagConstraints c, String label,
                            JComboBox<Integer> year,
                            JComboBox<Integer> month,
                            JComboBox<Integer> day) {

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.add(year);
        p.add(month);
        p.add(day);

        c.gridx = 0; c.gridy = y; add(new JLabel(label), c);
        c.gridx = 1; add(p, c);
    }

    private void populateDateBoxes(JComboBox<Integer> year,
                                   JComboBox<Integer> month,
                                   JComboBox<Integer> day) {

        int currentYear = AppMediator.getToday().getYear();
        for (int y = currentYear; y <= currentYear + 10; y++) year.addItem(y);
        for (int m = 1; m <= 12; m++) month.addItem(m);

        year.setSelectedItem(currentYear);
        month.setSelectedItem(AppMediator.getToday().getMonthValue());

        updateDays(year, month, day);
    }

    private void updateDays(JComboBox<Integer> year,
                            JComboBox<Integer> month,
                            JComboBox<Integer> day) {

        int y = (Integer) year.getSelectedItem();
        int m = (Integer) month.getSelectedItem();
        int max = YearMonth.of(y, m).lengthOfMonth();

        day.removeAllItems();
        for (int d = 1; d <= max; d++) day.addItem(d);
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
            String rf = rfField.getText().trim();
            String customerID = customerField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());
            LocalDate dueDate = getDueDate();
            LocalDate startDate = getStartDate();

            if (billNumber.isEmpty() || rf.isEmpty() || customerID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (startDate.isAfter(dueDate)) {
                JOptionPane.showMessageDialog(this, "Issue Date cannot be later that Due Date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Customer customer = UserManager.getInstance().findCustomerByID(customerID);
            if (customer == null) {
                JOptionPane.showMessageDialog(this, "Customer with ID " + customerID + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Bills tempBill = BillManager.getInstance().billWithThisRf(rf);
            if (tempBill != null && tempBill.getIssuer() != currentBusiness && tempBill.getCustomer() != customer && tempBill.getIssueDate().isBefore(startDate) && tempBill.getDueDate().isAfter(startDate)) {
                JOptionPane.showMessageDialog(this, "RF code " + rf + " cannot be used.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BillManager.getInstance().create(
                    rf,
                    billNumber,
                    (int) amount,
                    currentBusiness,
                    customer,
                    startDate,
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
        rfField.setText("");
        customerField.setText("");
        amountField.setText("");
    }
}