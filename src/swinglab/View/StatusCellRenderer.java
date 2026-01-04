package swinglab.View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatusCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        String status = String.valueOf(value);

        setHorizontalAlignment(CENTER);
        setFont(getFont().deriveFont(Font.BOLD));

        if (!isSelected) {
            switch (status) {
                case "PENDING":
                    setForeground(new Color(180, 140, 0)); // yellow/orange
                    break;
                case "ACCEPTED":
                    setForeground(new Color(0, 140, 0));   // green
                    break;
                case "REJECTED":
                    setForeground(Color.RED.darker());
                    break;
                case "PAID":
                    setForeground(new Color(0, 140, 0));
                    break;
                case "EXPIRED":
                    setForeground(Color.RED.darker());
                    break;
                case "PAUSED":
                    setForeground(new Color(180, 140, 100));
                    break;
                case "ACTIVE":
                    setForeground(new Color(0, 140, 0));   // green
                    break;
                default:
                    setForeground(Color.BLACK);
            }
        }

        return this;
    }
}
