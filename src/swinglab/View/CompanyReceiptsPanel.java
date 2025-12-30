package swinglab.View;

import Entities.Users.Business;
import Entities.Users.Bills;
import Entities.Users.BillStatus;
import Managers.BillManager;
import swinglab.View.AppMediator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class CompanyReceiptsPanel extends JPanel implements ActionListener {

    private final JTable receiptsTable;
    private final DefaultTableModel tableModel;
    private final JComboBox<String> statusFilterComboBox;
    private final JTextField startDateField;
    private final JTextField endDateField;
    private final JButton filterButton;
    private final JButton clearFilterButton;
    private final JButton refreshButton;
    private final JLabel totalLabel;

    private final String[] columnNames = {
            "Bill Number", "RF Code", "Customer", "Amount", "Status", "Issue Date", "Due Date"
    };

    public CompanyReceiptsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titleLabel = new JLabel("Company Receipts", SwingConstants.LEFT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        add(titleLabel, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filters"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        statusFilterComboBox = new JComboBox<>(new String[]{"ALL", "PENDING", "PAID", "EXPIRED"});
        filterPanel.add(statusFilterComboBox, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("From Date:"), gbc);
        gbc.gridx = 3;
        startDateField = new JTextField(10);
        startDateField.setText(LocalDate.now().minusMonths(1).toString());
        filterPanel.add(startDateField, gbc);

        gbc.gridx = 4;
        filterPanel.add(new JLabel("To Date:"), gbc);
        gbc.gridx = 5;
        endDateField = new JTextField(10);
        endDateField.setText(LocalDate.now().toString());
        filterPanel.add(endDateField, gbc);

        gbc.gridx = 6;
        filterButton = new JButton("Apply Filter");
        filterButton.addActionListener(this);
        filterPanel.add(filterButton, gbc);

        gbc.gridx = 7;
        clearFilterButton = new JButton("Clear Filters");
        clearFilterButton.addActionListener(this);
        filterPanel.add(clearFilterButton, gbc);

        gbc.gridx = 8;
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        filterPanel.add(refreshButton, gbc);

        add(filterPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        receiptsTable = new JTable(tableModel);
        receiptsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receiptsTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(receiptsTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        totalLabel = new JLabel("Total Amount: €0.00");
        totalLabel.setFont(totalLabel.getFont().deriveFont(Font.BOLD, 14f));
        tablePanel.add(totalLabel, BorderLayout.SOUTH);

        add(tablePanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == filterButton) {
            applyFilters();
        } else if (e.getSource() == clearFilterButton) {
            clearFilters();
        } else if (e.getSource() == refreshButton) {
            loadReceipts();
        }
    }

    public void loadReceipts() {
        tableModel.setRowCount(0);

        Business currentBusiness = getCurrentBusiness();
        if (currentBusiness == null) {
            JOptionPane.showMessageDialog(this, "No business user logged in.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<Bills> companyBills = BillManager.getInstance().findCompanyIssuedBills(currentBusiness.getUserId());
        displayBills(companyBills);
    }

    private void applyFilters() {
        Business currentBusiness = getCurrentBusiness();
        if (currentBusiness == null) {
            JOptionPane.showMessageDialog(this, "No business user logged in.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<Bills> companyBills = BillManager.getInstance().findCompanyIssuedBills(currentBusiness.getUserId());

        String selectedStatus = (String) statusFilterComboBox.getSelectedItem();
        if (!"ALL".equals(selectedStatus)) {
            BillStatus status = BillStatus.valueOf(selectedStatus);
            companyBills.removeIf(bill -> bill.getStatus() != status);
        }

        try {
            LocalDate startDate = LocalDate.parse(startDateField.getText().trim());
            LocalDate endDate = LocalDate.parse(endDateField.getText().trim());
            companyBills.removeIf(bill -> bill.getIssueDate().isBefore(startDate) || bill.getIssueDate().isAfter(endDate));
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        displayBills(companyBills);
    }

    private void clearFilters() {
        statusFilterComboBox.setSelectedItem("ALL");
        startDateField.setText(LocalDate.now().minusMonths(1).toString());
        endDateField.setText(LocalDate.now().toString());
        loadReceipts();
    }

    private void displayBills(ArrayList<Bills> bills) {
        tableModel.setRowCount(0);
        double total = 0.0;

        for (Bills bill : bills) {
            Object[] row = {
                    bill.getBillNumber(),
                    bill.getRF(),
                    bill.getCustomer() != null ? bill.getCustomer().getFullName() : "N/A",
                    String.format("€%.2f", bill.getAmount()),
                    bill.getStatus().toString(),
                    bill.getIssueDate().toString(),
                    bill.getDueDate().toString()
            };
            tableModel.addRow(row);
            total += bill.getAmount();
        }

        totalLabel.setText(String.format("Total Amount: €%.2f", total));
    }

    private Business getCurrentBusiness() {
        var user = AppMediator.getUser();
        return (user instanceof Business) ? (Business) user : null;
    }
}