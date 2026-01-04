package swinglab.View;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ActiveStandingOrdersPanel extends JPanel  {
    private final JTable table;
    private final DefaultTableModel model;
    private final JButton btnEdit, btnDelete, btnClose;


    public ActiveStandingOrdersPanel() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        // ---- Title ----
        JLabel title = new JLabel(" Standing  Orders");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        // ---- Table Data ----
        String[] cols = { "OrderId","Title", "Type", "Start Date", "End Date","Next Execution","Source Iban","Status" };
//        Object[][] data = {
//            { "ORD-001", "Payment", "-75.50", "2025-09-01", "2025-11-29", "Active" },
//            { "ORD-002", "Transfer", "-120.00", "2025-10-10", "2025-12-10", "Active" },
//            { "ORD-003", "Payment", "-45.00", "2025-09-15", "2025-11-15", "Paused" }
//        };
        Object[][] rows = {};
        model = new DefaultTableModel(rows, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);
        // 🔹 Status coloring
        table.getColumnModel().getColumn(7)
                .setCellRenderer(new StatusCellRenderer());

        // Custom renderer for amount (red for negative)

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---- Bottom Buttons ----
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnEdit = new JButton("Show Failed");
        btnDelete = new JButton("View Details");
        btnClose = new JButton("Close");


        south.add(btnEdit);
        south.add(btnDelete);
        south.add(btnClose);

        add(south, BorderLayout.SOUTH);



    }


    public DefaultTableModel getModel() {
        return model;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnClose() {
        return btnClose;
    }



    public void clearTable() {
        model.setRowCount(0);
    }

    public void addAccountRow(String orderID,String Title,String Type, String StartDate,String EndDate,String NextExecution,String SourceIban,String Status ) {
        model.addRow(new Object[] { orderID,Title, Type, StartDate, EndDate,Status.equals("EXPIRED")?"-":NextExecution,SourceIban,Status });
        TableColumn idColumn = table.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setPreferredWidth(0);

    }

    public void clearSelection() {
        table.clearSelection();
    }

    public JTable getTable() {
        return table;
    }

}
