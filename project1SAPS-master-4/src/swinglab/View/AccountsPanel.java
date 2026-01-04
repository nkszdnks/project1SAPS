package swinglab.View;

import Entities.Accounts.BankAcount;
import Managers.AccountManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class AccountsPanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;
    public JButton details, clearSel, closePan,newAcount;


    public AccountsPanel() {

        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel title = new JLabel("My Accounts");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        String[] cols = { "IBAN", "Main_Owner","Balance"};
        Object[][] rows = {};
//        Object[][] data = {
//            { "GR100000000000000001", "jjjj", "1,245.50" },
//            { "GR100000000000000002", "kkkk", "3,010.00" },
//            { "GR200000000000000001", "ssss", "25,730.90" }
//        };

        model = new DefaultTableModel(rows, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };


        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(20);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        details = new JButton("View Details");   // <-- NEW
        clearSel = new JButton("Clear Selection");
        closePan = new JButton("Close");
        newAcount = new JButton("Open New Acount");
        south.add(details);
        south.add(clearSel);
        south.add(closePan);
        south.add(newAcount);
        add(south, BorderLayout.SOUTH);


    }

    public void showSelectedRowDetails() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(viewRow);

        String iban    = String.valueOf(model.getValueAt(modelRow, 0));
        BankAcount b = AccountManager.getInstance().findAccountByIBAN(iban);
        String type    = String.valueOf(model.getValueAt(modelRow, 1));
        String balance = String.valueOf(model.getValueAt(modelRow, 2));
        String interest = String.valueOf(b.getInterestRate());

        // ==== A) QUICK POPUP (JOptionPane) ====
        // String msg = "IBAN: " + iban + "\nType: " + type + "\nBalance: " + balance;
        // JOptionPane.showMessageDialog(this, msg, "Account Details", JOptionPane.INFORMATION_MESSAGE);

        // ==== B) NICE POPUP (JDialog) ====
        AccountDetailsDialog dialog = new AccountDetailsDialog(
                SwingUtilities.getWindowAncestor(this), iban, type, balance,interest,b);
        dialog.setVisible(true);
    }
    private void openNewAcount() {
    	AppMediator.getCardLayout().show(AppMediator.getCards(),"newAcount");
    }

    public void clearTable() {
        model.setRowCount(0);
    }

    public void addAccountRow(String iban, String owner, double balance) {
        model.addRow(new Object[] { iban, owner, AppMediator.euroFormat.format(balance) });
    }

    public void clearSelection() {
        table.clearSelection();
    }

}


