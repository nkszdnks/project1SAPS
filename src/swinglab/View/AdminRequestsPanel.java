package swinglab.View;

import Entities.Accounts.BankAcount;
import Entities.AdminRequests.AdminRequest;
import Managers.AccountManager;
import Managers.AdminRequestsManager;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminRequestsPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel model;

    public JButton detailsBtn, closeBtn;

    public AdminRequestsPanel() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel title = new JLabel("Pending Admin Requests");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        String[] cols = {
                "Request ID", "Type", "User", "Status"
        };

        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(24);
        table.setFillsViewportHeight(true);

        // 🔹 Status coloring
        table.getColumnModel().getColumn(3)
                .setCellRenderer(new StatusCellRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        detailsBtn = new JButton("View Details");
        closeBtn   = new JButton("Close");


        actions.add(detailsBtn);
        actions.add(closeBtn);

        add(actions, BorderLayout.SOUTH);
    }
    public void clearTable() {
        model.setRowCount(0);
    }

    public void addRequestRow(String id, String type,
                              String user, String status) {
        model.addRow(new Object[] {
                id, type, user, status
        });
    }
    public void showSelectedRowDetails() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(viewRow);

        String RequestID    = String.valueOf(model.getValueAt(modelRow, 0));
        AdminRequest adminRequest = AdminRequestsManager.getInstance().findRequestByID(RequestID);
        String type    = String.valueOf(model.getValueAt(modelRow, 1));
        String customerName = String.valueOf(model.getValueAt(modelRow, 2));
        String status    = String.valueOf(model.getValueAt(modelRow, 3));


        // ==== A) QUICK POPUP (JOptionPane) ====
        // String msg = "IBAN: " + iban + "\nType: " + type + "\nBalance: " + balance;
        // JOptionPane.showMessageDialog(this, msg, "Account Details", JOptionPane.INFORMATION_MESSAGE);

        // ==== B) NICE POPUP (JDialog) ====
        RequestsDetailsDialog dialog = new RequestsDetailsDialog(
                SwingUtilities.getWindowAncestor(this),RequestID, type,customerName ,status,adminRequest);
        dialog.setVisible(true);
    }
}
