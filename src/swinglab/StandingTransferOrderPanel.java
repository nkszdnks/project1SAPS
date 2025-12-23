package swinglab;

import Entities.Accounts.BankAcount;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import javax.swing.*;

public class StandingTransferOrderPanel extends JPanel  implements  hasIbanField{

    private final JComboBox<String> fromIbans = new JComboBox<>();
    private final JTextField toIban = new JTextField(20);
    private final JTextField amount = new JTextField(10);
    private final JTextField maxAmount = new JTextField(10);
    private final JTextField title = new JTextField(20);
    private final JTextField description = new JTextField(20);

    private final JComboBox<Integer> executionDay = new JComboBox<>();
    private final JComboBox<Integer> frequencyMonths = new JComboBox<>();

    private final JComboBox<Integer> startYear = new JComboBox<>();
    private final JComboBox<Integer> startMonth = new JComboBox<>();
    private final JComboBox<Integer> startDay = new JComboBox<>();

    private final JComboBox<Integer> endYear = new JComboBox<>();
    private final JComboBox<Integer> endMonth = new JComboBox<>();
    private final JComboBox<Integer> endDay = new JComboBox<>();

    private final JButton btnFinish = new JButton("Finish");
    private final JButton btnClose = new JButton("Close");

    private final GridBagConstraints c = new GridBagConstraints();

    public StandingTransferOrderPanel() {

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;
        startYear.setBackground(Color.green);
        startMonth.setBackground(Color.green);
        startDay.setBackground(Color.green);

        endYear.setBackground(Color.red);
        endMonth.setBackground(Color.red);
        endDay.setBackground(Color.red);
        executionDay.setBackground(Color.orange);
        frequencyMonths.setBackground(Color.orange);

        /* ---------- Fields ---------- */
        addRow(0, "From IBAN:", fromIbans);
        addRow(1, "To IBAN:", toIban);
        addRow(2, "Amount (€):", amount);

        /* ---------- Execution day ---------- */
        for (int d = 1; d <= 28; d++) executionDay.addItem(d);
        addRow(3, "Execution day of month:", executionDay);

        /* ---------- Frequency ---------- */
        for (int m = 1; m <= 12; m++) frequencyMonths.addItem(m);
        addRow(4, "Repeat every (months):", frequencyMonths);

        /* ---------- Start Date ---------- */
        populateDateBoxes(startYear, startMonth, startDay);
        addDateRow(5, "Start Date:", startYear, startMonth, startDay);

        /* ---------- End Date ---------- */
        populateDateBoxes(endYear, endMonth, endDay);
        addDateRow(6, "End Date:", endYear, endMonth, endDay);

        /* ---------- Description ---------- */
        addRow(7, "Description:", description);
        addRow(8, "Title:", title);
        addRow(9, "Max Amount:", maxAmount);

        /* ---------- Buttons ---------- */
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnFinish);

        c.gridx = 0; c.gridy = 10; c.gridwidth = 2;
        add(buttons, c);



        /* ---------- Date listeners ---------- */
        startYear.addActionListener(e -> updateDays(startYear, startMonth, startDay));
        startMonth.addActionListener(e -> updateDays(startYear, startMonth, startDay));
        endYear.addActionListener(e -> updateDays(endYear, endMonth, endDay));
        endMonth.addActionListener(e -> updateDays(endYear, endMonth, endDay));
    }
    // ----- Allow Controller to set business options -----
    public void setIbans(ArrayList<BankAcount> accounts) {
        fromIbans.removeAllItems();
        for (BankAcount b : accounts)
            fromIbans.addItem(b.getIBAN());
    }
    // listeners
    public void addFinishListener(ActionListener l) {
        btnFinish.addActionListener(l);
    }

    public void addCloseListener(ActionListener l) {
        btnClose.addActionListener(l);
    }

    // getters
    public String getFromIban() { return String.valueOf(fromIbans.getSelectedItem()); }
    public String getToIban() { return toIban.getText(); }
    public String getAmount() { return amount.getText(); }
    public String getDescription() { return description.getText(); }

    public int getExecutionDay() {
        return (Integer) executionDay.getSelectedItem();
    }

    public int getFrequencyMonths() {
        return (Integer) frequencyMonths.getSelectedItem();
    }

    public LocalDate getStartDate() {
        return LocalDate.of(
                (Integer) startYear.getSelectedItem(),
                (Integer) startMonth.getSelectedItem(),
                (Integer) startDay.getSelectedItem()
        );
    }

    public LocalDate getEndDate() {
        return LocalDate.of(
                (Integer) endYear.getSelectedItem(),
                (Integer) endMonth.getSelectedItem(),
                (Integer) endDay.getSelectedItem()
        );
    }

    public String getTitle() {
        return title.getText();
    }

    // feedback
    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }


    /* ---------- Helpers ---------- */

    private void addRow(int y, String label, JComponent field) {
        c.gridx = 0; c.gridy = y; add(new JLabel(label), c);
        c.gridx = 1; add(field, c);
    }

    private void addDateRow(int y, String label,
                            JComboBox<Integer> year,
                            JComboBox<Integer> month,
                            JComboBox<Integer> day) {

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.add(year); p.add(month); p.add(day);

        c.gridx = 0; c.gridy = y; add(new JLabel(label), c);
        c.gridx = 1; add(p, c);
    }

    private void populateDateBoxes(JComboBox<Integer> year,
                                   JComboBox<Integer> month,
                                   JComboBox<Integer> day) {

        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear; y <= currentYear + 10; y++) year.addItem(y);
        for (int m = 1; m <= 12; m++) month.addItem(m);

        year.setSelectedItem(currentYear);
        month.setSelectedItem(LocalDate.now().getMonthValue());

        updateDays(year, month, day);
    }

    private void updateDays(JComboBox<Integer> year,
                            JComboBox<Integer> month,
                            JComboBox<Integer> day) {

        int y = (Integer) year.getSelectedItem();
        int m = (Integer) month.getSelectedItem();
        int max = YearMonth.of(y, m).lengthOfMonth();

        day.removeAllItems();
        for (int d = 1; d <= max; d++) day.addItem(d);
    }

    public String getMaxAmount() {
        return maxAmount.getText();
    }
}
