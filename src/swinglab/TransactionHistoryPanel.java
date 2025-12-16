package swinglab;

import Entities.Accounts.BankAcount;
import Entities.Accounts.Statements.Statement;
import swinglab.Contollers.TransactionHistoryController;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class TransactionHistoryPanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;
    public JButton  closePan;
    
    public TransactionHistoryPanel() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel title = new JLabel("History of Transactions ");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        String[] cols = { "Date/Time","Type"," From → To", " Amount" , " Balance" ," Status"};
        Object[][] data = {};

        model = new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // 🔹 Apply custom renderer for the "Amount" column
        TableColumn amountCol = table.getColumnModel().getColumn(3);
        amountCol.setCellRenderer(new AmountCellRenderer());

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePan = new JButton("Close");

        south.add(closePan);
        add(south, BorderLayout.SOUTH);

        // events


    }


    public void fillTable(java.util.List<Statement> statements) {
        model.setRowCount(0); // clear old rows

        for (Statement st : statements) {
            model.addRow(new Object[]{
                    st.getTimestamp(),
                    st.getDescription(),
                    st.getIbansInvolved()[0] + " → " + (st.getIbansInvolved()[1].isEmpty() ? "—" : st.getIbansInvolved()[1]),
                    formatAmount(st.getAmount(),st.getIbansInvolved()),
                    formatBalance(st.getBalanceAfter(),st.getIbansInvolved()),
                    "Completed"
            });
        }
    }

    private String formatAmount(double amount,String[] ibansInvolved) {
        if (TransactionHistoryController.getInstance().getModel().getIBAN().equals(ibansInvolved[0])) {


        return String.format("-"+"%.2f", amount);
    }
    else {
            return String.format("%+.2f", amount);

        }
    }

    private String formatBalance(double[] b,String[] ibansInvolved) {
        if (b[1] == 0)
            return String.format("%.2f", b[0]);
        if(TransactionHistoryController.getInstance().getModel().getIBAN().equals(ibansInvolved[0])) {
            return String.format("%.2f", b[0]);
        }
        return String.format("%.2f", b[1]);
    }

	/**
     * 🔸 Inner class: custom renderer for coloring positive/negative amounts.
     */
    static class AmountCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            if (value != null) {
                String text = value.toString().trim();
                if (text.startsWith("+"))
                    c.setForeground(new Color(0, 128, 0)); // green
                else if (text.startsWith("-"))
                    c.setForeground(Color.RED);
                else
                    c.setForeground(Color.BLACK);
            }
            // keep background consistent when selected
            if (isSelected)
                c.setBackground(table.getSelectionBackground());
            else
                c.setBackground(Color.WHITE);

            setHorizontalAlignment(SwingConstants.RIGHT);
            return c;
        }
    }

}


