package swinglab.View;

import Entities.Accounts.Statements.Statement;
import Managers.AccountManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AllStatementsPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel model;
    private JComboBox<String> dateFilter;
    private JComboBox<String> amountFilter;

    private JTextField fromDate, toDate;
    private JTextField minAmount, maxAmount;

    private JButton applyFilter, clearFilter;


    public JButton details, clearSel, closePan;



    public AllStatementsPanel() {

        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel title = new JLabel("All Statements");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        String[] cols = { "Date","Description", "IBANS involved", "Amount","Fee","Account Balance 1","Account Balance 2","Status" };
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        details = new JButton("View Details");
        clearSel = new JButton("Clear Selection");
        closePan = new JButton("Close");

        south.add(details);
        south.add(clearSel);
        south.add(closePan);

        add(south, BorderLayout.SOUTH);
    }

    /* ================= TABLE METHODS ================= */

    public void clearTable() {
        model.setRowCount(0);
    }

    public void addStatementRow(Statement t) {
        model.addRow(new Object[]{
                t.getTimestamp(),
                t.getDescription(),
                t.getIbansInvolved()[0] + " → " + (t.getIbansInvolved()[1].isEmpty() ? "—" : t.getIbansInvolved()[1]),
                AppMediator.euroFormat.format(t.getAmount()),
                AppMediator.euroFormat.format(t.getFee()),
                AppMediator.euroFormat.format(t.getBalanceAfter()[0]),
                t.getBalanceAfter()[1]==0.0 ?"-":AppMediator.euroFormat.format(t.getBalanceAfter()[1]),
                "Completed"
        });
    }

    public void clearSelection() {
        table.clearSelection();
    }

    public void showSelectedRowDetails() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a statement first.",
                    "No selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = table.convertRowIndexToModel(viewRow);

        String Date = String.valueOf(model.getValueAt(modelRow, 0));
        String IBAN  = String.valueOf(model.getValueAt(modelRow, 2));
        String Amount = String.valueOf(model.getValueAt(modelRow, 3));
        String Fee = String.valueOf(model.getValueAt(modelRow, 4));
        String []ibans = IBAN.split("→",2);
        String SenderName = AccountManager.getInstance().findAccountByIBAN(ibans[0].trim()).getCustomer()!= null ?AccountManager.getInstance().findAccountByIBAN(ibans[0].trim()).getCustomer().getFullName():"Bank Of TUC";
        String RecipientName = "-";
        if(!ibans[1].trim().isEmpty() && AccountManager.getInstance().findAccountByIBAN(ibans[1].trim()) != null) {
            RecipientName = AccountManager.getInstance().findAccountByIBAN(ibans[1].trim()).getCustomer()!= null?AccountManager.getInstance().findAccountByIBAN(ibans[1].trim()).getCustomer().getFullName():"Bank Of TUC";
        }

        String msg =
                "Date: " + Date+ "\n" +
                        "IBAN: " + IBAN + "\n" +
                        "Description: " + model.getValueAt(modelRow, 1) + "\n" +
                        "Amount: " + Amount + "\n" +
                        "Fee: " + Fee + "\n" +
                        "Sender: "+SenderName+"\n"+
                        "Recipient: "+ RecipientName+"\n" +
                        "Status: " + model.getValueAt(modelRow, 7);

        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                msg,
                "Statement Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
