package swinglab.View;

import Entities.Transactions.Requests.TransferRequest;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ScheduledTransfersPanel extends JPanel  {
    private final JTable table;
    private final DefaultTableModel model;
    private final JButton btnDelete, btnClose;


    public ScheduledTransfersPanel() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        // ---- Title ----
        JLabel title = new JLabel(" Scheduled Transfers");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        // ---- Table Data ----
        String[] cols = { "OrderId","amount", "reason","receiver IBAN","scheduled date"};//, "Start Date", "End Date","Next Execution","Source Iban","Status" };
        Object[][] rows = {};
        model = new DefaultTableModel(rows, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);
        // 🔹 Status coloring
        table.getColumnModel().getColumn(4)
                .setCellRenderer(new StatusCellRenderer());

        // Custom renderer for amount (red for negative)

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---- Bottom Buttons ----
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        btnDelete = new JButton("Delete");
        btnClose = new JButton("Close");


        south.add(btnDelete);
        south.add(btnClose);

        add(south, BorderLayout.SOUTH);



    }


    public DefaultTableModel getModel() {
        return model;
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

    public void addAccountRow(TransferRequest req, String Amount, String Type, String ReceiverIBAN, String Date){//,String SourceIban,String Status ) {
        model.addRow(new Object[] {req,Amount, Type, ReceiverIBAN, Date});//Status.equals("EXPIRED")?"-":NextExecution,SourceIban,Status });
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
