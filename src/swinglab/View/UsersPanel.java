package swinglab.View;

import Entities.Users.Customer;
import Entities.Users.User;
import Entities.Users.UserRole;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class UsersPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel model;

    public JButton details, clearSel, closePan;

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public UsersPanel() {

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("All Users");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        String[] cols = { "Username", "Full Name", "Role", "Last Login" };
        Object[][] rows = {};

        model = new DefaultTableModel(rows, cols) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(20);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        details  = new JButton("View Details");
        clearSel = new JButton("Clear Selection");
        closePan = new JButton("Close");

        south.add(details);
        south.add(clearSel);
        south.add(closePan);

        add(south, BorderLayout.SOUTH);
    }

    /* ================== DATA METHODS ================== */

    public void clearTable() {
        model.setRowCount(0);
    }

    public void addUserRow(User user) {

        String username = user.getUsername();
        String role = user.getRole().name();

        String fullName = (user instanceof Customer c)
                ? c.getFullName()
                : "-";

        String lastLogin = (user.getLastLogin() != null)
                ? user.getLastLogin().format(DATE_FMT)
                : "Never";

        model.addRow(new Object[] {
                username,
                fullName,
                role,
                lastLogin
        });
    }

    /* ================== SELECTION ================== */

    public void clearSelection() {
        table.clearSelection();
    }

    public void showSelectedUserDetails() {

        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first.",
                    "No selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = table.convertRowIndexToModel(viewRow);

        String username = String.valueOf(model.getValueAt(modelRow, 0));
        String role     = String.valueOf(model.getValueAt(modelRow, 2));

        JOptionPane.showMessageDialog(
                this,
                "Username: " + username + "\nRole: " + role,
                "User Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
