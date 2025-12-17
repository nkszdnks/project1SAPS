package swinglab;

import Entities.Accounts.PersonalAccount;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class CoOwnersPanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;
    public JButton removeSelected,addNew, clearSel, closePan;

    
    public CoOwnersPanel() {


        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel title = new JLabel("Co-Owners ");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        String[] cols = { "Name","AFM"};
        Object[][] data = {};

        model = new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        removeSelected = new JButton("RemoveSelected");   // <-- NEW
        clearSel = new JButton("Clear Selection");
        closePan = new JButton("Close");
        addNew = new JButton("Add new"); 
        south.add(removeSelected);
        south.add(addNew);
        south.add(clearSel);
        south.add(closePan);
        add(south, BorderLayout.SOUTH);


    }

    private void showSelectedRowDetails() {

    }
    public void addCoOwnerRow(String name, String afm) {
        model.addRow(new Object[] { name, afm });
    }

    public void clearTable() {
        model.setRowCount(0);
    }


    public String getValueAt(int row, int col) {
        return String.valueOf(model.getValueAt(row, col));
    }

    public String getNameAtRow(int row) {
        return String.valueOf(model.getValueAt(row, 0));   // column 0 = Name
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public void removeRow(int row) {
        model.removeRow(row);
    }

    public void clearSelection() {
        table.clearSelection();
    }


}


