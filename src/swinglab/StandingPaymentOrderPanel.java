package swinglab;

import Entities.Accounts.BankAcount;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import javax.swing.*;

public class StandingPaymentOrderPanel extends JPanel implements hasIbanField{

    private final JComboBox<String> fromIbans = new JComboBox<>();
    private final JTextField rfCode = new JTextField();
    private final JComboBox<String> businessList = new JComboBox<>();

    private final JTextField maxAmount = new JTextField(10);
    private final JTextField title = new JTextField(20);
    private final JTextField description = new JTextField(20);


    private final JComboBox<Integer> startYear = new JComboBox<>();
    private final JComboBox<Integer> startMonth = new JComboBox<>();
    private final JComboBox<Integer> startDay = new JComboBox<>();

    private final JComboBox<Integer> endYear = new JComboBox<>();
    private final JComboBox<Integer> endMonth = new JComboBox<>();
    private final JComboBox<Integer> endDay = new JComboBox<>();

    private final JButton btnFinish = new JButton("Finish");
    private final JButton btnClose = new JButton("Close");

    private final GridBagConstraints c = new GridBagConstraints();

    public StandingPaymentOrderPanel() {

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        /* ---------- Fields ---------- */
        addRow(0, "From IBAN:", fromIbans);
        addRow(1, "RF Code:", rfCode);
        addRow(2, "Business Issuer:", businessList);
        addRow(3, "Max Amount (€):", maxAmount);


        /* ---------- Start Date ---------- */
        populateDateBoxes(startYear, startMonth, startDay);
        addDateRow(4, "Start Date:", startYear, startMonth, startDay);

        /* ---------- End Date ---------- */
        populateDateBoxes(endYear, endMonth, endDay);
        addDateRow(5, "End Date:", endYear, endMonth, endDay);

        /* ---------- Description ---------- */
        addRow(6, "Description:", description);
        addRow(7, "Title:", title);

        /* ---------- Buttons ---------- */
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnFinish);

        c.gridx = 0; c.gridy = 8; c.gridwidth = 2;
        add(buttons, c);

        /* ---------- Date listeners ---------- */
        startYear.addActionListener(e -> updateDays(startYear, startMonth, startDay));
        startMonth.addActionListener(e -> updateDays(startYear, startMonth, startDay));
        endYear.addActionListener(e -> updateDays(endYear, endMonth, endDay));
        endMonth.addActionListener(e -> updateDays(endYear, endMonth, endDay));
    }

    /* ===== Allow Controller to inject data ===== */

    public void setIbans(ArrayList<BankAcount> accounts) {
        fromIbans.removeAllItems();
        for (BankAcount b : accounts)
            fromIbans.addItem(b.getIBAN());
    }
    public String getSelectedBusiness() {
        return (String) businessList.getSelectedItem();
    }

    // ----- Allow Controller to set business options -----
    public void setBusinesses(java.util.List<String> businesses) {
        businessList.removeAllItems();
        for (String b : businesses)
            businessList.addItem(b);
    }



    /* ===== Listeners ===== */

    public void addFinishListener(ActionListener l) {
        btnFinish.addActionListener(l);
    }

    public void addCloseListener(ActionListener l) {
        btnClose.addActionListener(l);
    }

    /* ===== Getters ===== */

    public String getFromIban() {
        return String.valueOf(fromIbans.getSelectedItem());
    }

    public String getRfCode() {
        return String.valueOf(rfCode.getText());
    }

    public String getMaxAmount() {
        return maxAmount.getText();
    }

    public String getTitle() {
        return title.getText();
    }

    public String getDescription() {
        return description.getText();
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

    /* ===== Feedback ===== */

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /* ===== Helpers ===== */

    private void addRow(int y, String label, JComponent field) {
        c.gridx = 0; c.gridy = y; add(new JLabel(label), c);
        c.gridx = 1; add(field, c);
    }

    private void addDateRow(int y, String label,
                            JComboBox<Integer> year,
                            JComboBox<Integer> month,
                            JComboBox<Integer> day) {

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.add(year);
        p.add(month);
        p.add(day);

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
}
