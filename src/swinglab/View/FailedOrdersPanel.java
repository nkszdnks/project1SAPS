package swinglab.View;

import swinglab.View.AppMediator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FailedOrdersPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel model;
    private final JButton btnBack;

    public FailedOrdersPanel() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // ---- Title ----
        JLabel title = new JLabel(" Failed Standing Orders");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        // ---- Table ----
        String[] cols = {
                "Title",
                "Type",
                "Amount",
                "Failure Date",
                "Source IBAN",
                "Failure Reason",
                "Attempts"
        };

        model = new DefaultTableModel(new Object[][]{}, cols) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(24);
        table.setFillsViewportHeight(true);
        table.setGridColor(Color.RED);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---- Bottom ----
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnBack = new JButton("Back");
        south.add(btnBack);
        add(south, BorderLayout.SOUTH);
    }

    /* ---------------- API for Controller ---------------- */

    public void clearTable() {
        model.setRowCount(0);
    }

    public void addFailedOrderRow(
            String title,
            String type,
            double amount,
            String date,
            String sourceIban,
            String reason,
            int attempts
    ) {
        model.addRow(new Object[]{
                title,
                type,
                AppMediator.euroFormat.format(amount),
                date,
                sourceIban,
                reason,
                attempts
        });
    }

    public JButton getBtnBack() {
        return btnBack;
    }
}
