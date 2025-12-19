package swinglab;

import Entities.Accounts.Statements.Statement;
import Entities.Transactions.Transaction;
import Managers.TransactionManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class AllStatementsPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel model;

    public JButton details, clearSel, closePan;

    private final NumberFormat euroFormat =
            NumberFormat.getCurrencyInstance(Locale.GERMANY);

    public AllStatementsPanel() {

        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel title = new JLabel("All Statements");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        String[] cols = { "Date","Description", "IBANS involved", "Amount","Account Balance 1","Account Balance 2","Status" };
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(20);

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
                euroFormat.format(t.getAmount()),
                t.getBalanceAfter()[0],
                t.getBalanceAfter()[1]==0.0 ?"-":t.getBalanceAfter()[1],
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

        String msg =
                "Date: " + model.getValueAt(modelRow, 0) + "\n" +
                        "IBAN: " + model.getValueAt(modelRow, 1) + "\n" +
                        "Description: " + model.getValueAt(modelRow, 2) + "\n" +
                        "Amount: " + model.getValueAt(modelRow, 3) + "\n" +
                        "Status: " + model.getValueAt(modelRow, 4);

        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                msg,
                "Statement Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
