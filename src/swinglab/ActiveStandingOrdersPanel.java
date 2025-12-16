package swinglab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

class ActiveStandingOrdersPanel extends JPanel implements ActionListener {
    private final JTable table;
    private final DefaultTableModel model;
    private final JButton btnEdit, btnDelete, btnClose,btnPause,btnActivate;


    public ActiveStandingOrdersPanel() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        // ---- Title ----
        JLabel title = new JLabel(" Standing  Orders");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        // ---- Table Data ----
        String[] cols = { "Order ID", "Type", "Amount (€)", "Start Date", "Next Execution", "Status" };
        Object[][] data = {
            { "ORD-001", "Payment", "-75.50", "2025-09-01", "2025-11-29", "Active" },
            { "ORD-002", "Transfer", "-120.00", "2025-10-10", "2025-12-10", "Active" },
            { "ORD-003", "Payment", "-45.00", "2025-09-15", "2025-11-15", "Paused" }
        };
        model = new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);

        // Custom renderer for amount (red for negative)
        TableColumn amountCol = table.getColumnModel().getColumn(3);
        amountCol.setCellRenderer(new AmountCellRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---- Bottom Buttons ----
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnEdit = new JButton("Edit Settings");
        btnDelete = new JButton("Delete Order");
        btnClose = new JButton("Close");
        btnPause = new JButton("Pause");
        btnActivate = new JButton("Activate");

        south.add(btnEdit);
        south.add(btnDelete);
        south.add(btnClose);
        south.add(btnPause);
        south.add(btnActivate);
        add(south, BorderLayout.SOUTH);

        // ---- Event Listeners ----
        btnEdit.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClose.addActionListener(this);
        btnPause.addActionListener(this);
        btnActivate.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        int row = table.getSelectedRow();

        if (src == btnClose) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "dashboard");
            return;
        }

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order first.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }



        if (src == btnEdit) {
        	    if (row == -1) {
        	        JOptionPane.showMessageDialog(this, "Select an order first.", "No selection", JOptionPane.WARNING_MESSAGE);
        	        return;
        	    }
          

        	}

         else if (src == btnDelete) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete standing order  ?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(row);
                JOptionPane.showMessageDialog(this, "Standing payment order  deleted.");
            }
        }

        else if (src == btnPause) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to pause the  standing order ?",
                    "Confirm Pause",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Standing payment order  paused.");
            }
        }
        else if (src == btnActivate) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to activate the  standing order ?",
                    "Confirm Pause",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Standing payment order is not active and will be executed automatically.");
            }
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
                    c.setForeground(new Color(0, 128, 0)); // green
                else if (text.startsWith("-"))
                    c.setForeground(Color.RED); // red
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
