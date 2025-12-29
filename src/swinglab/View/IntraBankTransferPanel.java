package swinglab.View;

import Entities.Accounts.BankAcount;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;

public class IntraBankTransferPanel extends JPanel implements hasIbanField {
    public final JComboBox<String> fromIbans = new JComboBox<>();
    public final JTextField toIban = new JTextField(20);
    public final JTextField amount = new JTextField(10);
    public final JTextField reason = new JTextField(20);
    public final JButton btnFinish = new JButton("Finish");
    public final JButton btnClose = new JButton("Close");

    // Date selection combo boxes
    public final JComboBox<Integer> dayBox = new JComboBox<>();
    public final JComboBox<Integer> monthBox = new JComboBox<>();
    public final JComboBox<Integer> yearBox = new JComboBox<>();
    public final JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    // Choice box to enable date selection
    public final JComboBox<String> scheduleChoice = new JComboBox<>(new String[]{"Immediate Transfer", "Schedule Transfer"});

    protected final GridBagConstraints c = new GridBagConstraints();

    IntraBankTransferPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx=0; c.gridy=0; add(new JLabel("From IBAN:"), c);
        c.gridx=1; add(fromIbans, c);

        c.gridx=0; c.gridy=1; add(new JLabel("To IBAN (same bank):"), c);
        c.gridx=1; add(toIban, c);

        c.gridx=0; c.gridy=2; add(new JLabel("Amount (€):"), c);
        c.gridx=1; add(amount, c);

        c.gridx=0; c.gridy=3; add(new JLabel("Reason:"), c);
        c.gridx=1; add(reason, c);

        // Schedule choice
        c.gridx=0; c.gridy=4; add(new JLabel("Transfer Option:"), c);
        c.gridx=1; add(scheduleChoice, c);

        // Prepare date panel but hide it initially
        for (int i = 1; i <= 31; i++) dayBox.addItem(i);
        Integer[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
        for (Integer m : months) monthBox.addItem(m);
        int currentYear = AppMediator.getToday().getYear();
        for (int i = 0; i <= 5; i++) yearBox.addItem(currentYear + i);

        datePanel.add(dayBox);
        datePanel.add(monthBox);
        datePanel.add(yearBox);
        datePanel.setVisible(false); // initially hidden
        c.gridx=1; c.gridy=5;
        add(datePanel, c);

        // Show/hide date panel based on choice
        scheduleChoice.addActionListener(e -> {
            boolean showDate = scheduleChoice.getSelectedIndex() == 1; // Schedule Transfer
            datePanel.setVisible(showDate);
            revalidate();
            repaint();
        });

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnFinish);
        c.gridwidth=2; c.gridx=0; c.gridy=6;
        add(buttons, c);
    }

    @Override
    public void setIbans(ArrayList<BankAcount> accounts) {
        fromIbans.removeAllItems();
        for (BankAcount b : accounts)
            fromIbans.addItem(b.getIBAN());
    }
    public LocalDate getSelectedDate() {
        if (scheduleChoice.getSelectedIndex() == 0) { // Immediate Transfer
            return AppMediator.getToday();
        }

        int day = (Integer) dayBox.getSelectedItem();
        int year = (Integer) yearBox.getSelectedItem();

        // Convert month string to Month enum
        int  month =(Integer) monthBox.getSelectedItem();


        return LocalDate.of(year, month, day);
    }
}


