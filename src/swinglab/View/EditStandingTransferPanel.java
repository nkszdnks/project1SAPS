package swinglab.View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class EditStandingTransferPanel extends JPanel implements ActionListener {

    private final JTextField orderId = new JTextField(10);
    private final JTextField fromIban = new JTextField(22);
    private final JTextField toIban = new JTextField(22);
    private final JTextField amount = new JTextField(10);
    private final JTextField description = new JTextField(20);
    private final JTextField nextExecDate = new JTextField(10);
    private final JComboBox<String> frequency; // can be changed by user
    private final JComboBox<String> status = new JComboBox<>(new String[]{"Active", "Paused"});
    private final JButton btnSave = new JButton("Save Changes");
    private final JButton btnPauseResume = new JButton("Pause/Resume");
    private final JButton btnClose = new JButton("Close");
    private final GridBagConstraints c = new GridBagConstraints();

    EditStandingTransferPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Edit Standing Transfer Order", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        c.gridx=0; c.gridy=0; c.gridwidth=2;
        add(title, c);
        c.gridwidth=1;

        c.gridx=0; c.gridy=1; add(new JLabel("Order ID:"), c);
        orderId.setEditable(false);
        c.gridx=1; add(orderId, c);

        c.gridx=0; c.gridy=2; add(new JLabel("From IBAN:"), c);
        c.gridx=1; add(fromIban, c);

        c.gridx=0; c.gridy=3; add(new JLabel("To IBAN:"), c);
        c.gridx=1; add(toIban, c);

        c.gridx=0; c.gridy=4; add(new JLabel("Amount (€):"), c);
        c.gridx=1; add(amount, c);

        c.gridx=0; c.gridy=5; add(new JLabel("Description:"), c);
        c.gridx=1; add(description, c);

        c.gridx=0; c.gridy=6; add(new JLabel("Next Execution (YYYY-MM-DD):"), c);
        c.gridx=1; add(nextExecDate, c);

        String[] freqOptions = { "Daily", "Weekly", "Monthly", "Quarterly", "Yearly" };
        frequency = new JComboBox<>(freqOptions);
        c.gridx=0; c.gridy=7; add(new JLabel("Frequency:"), c);
        c.gridx=1; add(frequency, c);

        c.gridx=0; c.gridy=8; add(new JLabel("Status:"), c);
        c.gridx=1; add(status, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnPauseResume);
        buttons.add(btnClose);
        buttons.add(btnSave);

        c.gridx=0; c.gridy=9; c.gridwidth=2;
        add(buttons, c);

        btnSave.addActionListener(this);
        btnPauseResume.addActionListener(this);
        btnClose.addActionListener(this);
    }

    public void loadData(String id, String from, String to, String amt, String desc, String next, String freq, String stat) {
        orderId.setText(id);
        fromIban.setText(from);
        toIban.setText(to);
        amount.setText(amt);
        description.setText(desc);
        nextExecDate.setText(next);
        frequency.setSelectedItem(freq);
        status.setSelectedItem(stat);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            JOptionPane.showMessageDialog(this,
                "Changes saved for Standing Transfer Order " + orderId.getText() + ".");
        }
        else if (e.getSource() == btnPauseResume) {
            String s = (String) status.getSelectedItem();
            if (s.equals("Active")) status.setSelectedItem("Paused");
            else status.setSelectedItem("Active");
        }
        else if (e.getSource() == btnClose) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "activeStandingPayments");
        }
    }
}
