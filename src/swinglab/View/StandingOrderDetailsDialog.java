package swinglab.View;

import Entities.StandingOrders.StandingOrder;
import Entities.StandingOrders.OrderStatus;

import javax.swing.*;
import java.awt.*;

public class StandingOrderDetailsDialog extends JDialog {

    public StandingOrderDetailsDialog(Window owner, StandingOrder s) {
        super(owner, "Standing Order Details", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;

        /* -------- Row 0 -------- */
        c.gridx = 0; c.gridy = 0;
        content.add(new JLabel("Title:"), c);
        c.gridx = 1;
        content.add(createField(s.getTitle()), c);

        /* -------- Row 1 -------- */
        c.gridx = 0; c.gridy = 1;
        content.add(new JLabel("Type:"), c);
        c.gridx = 1;
        content.add(createField(s.getType()), c);

        /* -------- Row 2 -------- */
        c.gridx = 0; c.gridy = 2;
        content.add(new JLabel("Amount (€):"), c);
        c.gridx = 1;
        content.add(createField(String.valueOf(s.getAmount())), c);

        /* -------- Row 3 -------- */
        c.gridx = 0; c.gridy = 3;
        content.add(new JLabel("Start Date:"), c);
        c.gridx = 1;
        content.add(createField(String.valueOf(s.getStartDate())), c);

        /* -------- Row 4 -------- */
        c.gridx = 0; c.gridy = 4;
        content.add(new JLabel("End Date:"), c);
        c.gridx = 1;
        content.add(createField(String.valueOf(s.getEndDate())), c);

        /* -------- Row 5 -------- */
        c.gridx = 0; c.gridy = 5;
        content.add(new JLabel("Next Execution:"), c);
        c.gridx = 1;
        content.add(createField(String.valueOf(s.getExecutionDate())), c);

        /* -------- Row 6 -------- */
        c.gridx = 0; c.gridy = 6;
        content.add(new JLabel("Source IBAN:"), c);
        c.gridx = 1;
        content.add(createField(s.getChargeIBAN()), c);

        /* -------- Row 7 -------- */
        c.gridx = 0; c.gridy = 7;
        content.add(new JLabel("Status:"), c);
        c.gridx = 1;
        content.add(createField(String.valueOf(s.getStatus())), c);

        /* -------- Buttons -------- */
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnClose = new JButton("Close");
        JButton btnPause = new JButton("Pause");
        JButton btnActivate = new JButton("Activate");

        buttons.add(btnClose);
        buttons.add(btnPause);
        buttons.add(btnActivate);

        // Status-based visibility
        if (s.getStatus() == OrderStatus.ACTIVE) {
            btnActivate.setVisible(false);
        } else {
            btnPause.setVisible(false);
        }

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.EAST;
        content.add(buttons, c);

        setContentPane(content);
        pack();
        setLocationRelativeTo(owner);

        /* -------- Actions -------- */
        btnClose.addActionListener(e -> dispose());

        btnPause.addActionListener(e -> {
            s.setStatus(OrderStatus.PAUSED);
            dispose();
        });

        btnActivate.addActionListener(e -> {
           s.setStatus(OrderStatus.ACTIVE);
           dispose();
        });
    }

    /* -------- Helper -------- */
    private JTextField createField(String value) {
        JTextField tf = new JTextField(value, 22);
        tf.setEditable(false);
        return tf;
    }
}
