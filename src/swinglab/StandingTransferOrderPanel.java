package swinglab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class StandingTransferOrderPanel extends JPanel implements ActionListener {

    private final JTextField fromIban = new JTextField(20);
    private final JTextField toIban = new JTextField(20);
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> frequency;   // επιλογές συχνότητας
    private final JTextField startDate = new JTextField(10);
    private final JTextField description = new JTextField(20);
    private final JButton btnFinish = new JButton("Finish");
    private final JButton btnClose = new JButton("Close");
    private final GridBagConstraints c = new GridBagConstraints();

    StandingTransferOrderPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        // --- Τίτλοι Πεδίων ---
        c.gridx=0; c.gridy=0; add(new JLabel("From IBAN:"), c);
        c.gridx=1; add(fromIban, c);

        c.gridx=0; c.gridy=1; add(new JLabel("To IBAN:"), c);
        c.gridx=1; add(toIban, c);

        c.gridx=0; c.gridy=2; add(new JLabel("Amount (€):"), c);
        c.gridx=1; add(amount, c);

        // 🔽 Frequency ως drop-down επιλογή
        String[] freqOptions = { 
            "Daily", 
            "Weekly", 
            "Every 2 Weeks", 
            "Monthly", 
            "Quarterly", 
            "Yearly" 
        };
        frequency = new JComboBox<>(freqOptions);

        c.gridx=0; c.gridy=3; add(new JLabel("Frequency:"), c);
        c.gridx=1; add(frequency, c);

        c.gridx=0; c.gridy=4; add(new JLabel("Start Date (YYYY-MM-DD):"), c);
        c.gridx=1; add(startDate, c);

        c.gridx=0; c.gridy=5; add(new JLabel("Description:"), c);
        c.gridx=1; add(description, c);

        // --- Buttons ---
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnFinish);

        c.gridwidth=2;
        c.gridx=0; c.gridy=6;
        add(buttons, c);
        frequency.addActionListener(this);
        btnFinish.addActionListener(this);
        btnClose.addActionListener(this);
        btnClose.setActionCommand("Close");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnFinish) {
            String freq = (String) frequency.getSelectedItem();
            JOptionPane.showMessageDialog(this,
                "Standing Order created successfully!\n" +
                "Frequency: " + freq + "\n" +
                "The system will execute it automatically at each due date.\n" +
                "Up to 3 failed attempts will be logged before skipping (per policy).");
        } 
        else if (e.getSource() == btnClose) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "transfersPanel");
        }// else if (e.getSource() == frequency) {

        //}
    }
}
