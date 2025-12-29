package swinglab.View;

import Entities.Users.Bills;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewAllBillsPanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;
    private JComboBox<String> dateFilter;
    private JComboBox<String> amountFilter;

    private JTextField fromDate, toDate;
    private JTextField minAmount, maxAmount;

    private JButton applyFilter, clearFilter;


    public JButton  closePan;


    public ViewAllBillsPanel() {

        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel title = new JLabel("All Statements");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        String[] cols = { "Bill Number","RF Code","Amount", "Business Name", "Customer Name" ,"Issue Date", "Due Date","Status"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };


        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);
        // 🔹 Status coloring
        table.getColumnModel().getColumn(7)
                .setCellRenderer(new StatusCellRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));


        closePan = new JButton("Close");


        south.add(closePan);

        add(south, BorderLayout.SOUTH);
    }

    /* ================= TABLE METHODS ================= */

    public void clearTable() {
        model.setRowCount(0);
    }

    public void addBillRow(Bills t) {
        model.addRow(new Object[]{
                t.getBillNumber(),
                t.getRF(),
                AppMediator.euroFormat.format(t.getAmount()),
                t.getIssuer().getBusinessName(),
                t.getCustomer().getFullName(),
                String.valueOf(t.getIssueDate()),
                String.valueOf(t.getDueDate()),
                t.getStatus()
        });
    }

    public void clearSelection() {
        table.clearSelection();
    }


}
