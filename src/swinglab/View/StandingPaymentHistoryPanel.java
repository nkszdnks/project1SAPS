package swinglab.View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StandingPaymentHistoryPanel extends JPanel implements ActionListener {
    private final JTable table;
    private final DefaultTableModel model;
    private final JButton btnClose;

    public StandingPaymentHistoryPanel() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        // ---- Title ----
        JLabel title = new JLabel("Standing Payment Orders - Execution History");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        // ---- Table Data ----
        String[] cols = { "Date/Time", "RF Code", "Amount (€)", "Status", "Comments" };
        Object[][] data = {
            { "2025-09-29 09:00", "RF123456789012345678", "-75.50", "Completed", "Executed successfully" },
            { "2025-10-29 09:00", "RF123456789012345678", "-75.50", "Completed", "Executed successfully" },
            { "2025-11-29 09:00", "RF123456789012345678", "-75.50", "Failed", "Insufficient funds (1/3)" },
            { "2025-12-29 09:00", "RF123456789012345678", "-75.50", "Scheduled", "Next execution planned" }
        };

        model = new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);

        // Custom renderer for Amount (green/red)
        TableColumn amountCol = table.getColumnModel().getColumn(2);
        amountCol.setCellRenderer(new AmountCellRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---- South Buttons ----
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnClose = new JButton("Close");
        south.add(btnClose);
        add(south, BorderLayout.SOUTH);

        // ---- Events ----
        btnClose.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClose) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "standingPaymentOrder");
        }
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
                    c.setForeground(new Color(0, 128, 0)); // green (credit)
                else if (text.startsWith("-"))
                    c.setForeground(Color.RED); // red (debit)
                else
                    c.setForeground(Color.BLACK);
            }

            if (isSelected)
                c.setBackground(table.getSelectionBackground());
            else
                c.setBackground(Color.WHITE);

            setHorizontalAlignment(SwingConstants.RIGHT);
            return c;
        }
    }
}
