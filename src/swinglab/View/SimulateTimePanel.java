package swinglab.View;

import swinglab.AppMediator;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class SimulateTimePanel extends JPanel {

    private JComboBox<Integer> yearBox;
    private JComboBox<Integer> monthBox;
    private JComboBox<Integer> dayBox;

    private JButton btnSimulate;
    private JButton btnBack;

    public SimulateTimePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Simulate Time");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        // Center panel
        JPanel center = new JPanel(new GridLayout(2, 4, 10, 10));

        center.add(new JLabel("Year"));
        center.add(new JLabel("Month"));
        center.add(new JLabel("Day"));
        center.add(new JLabel()); // spacer

        yearBox = new JComboBox<>();
        monthBox = new JComboBox<>();
        dayBox = new JComboBox<>();

        populateYears();
        populateMonths();
        updateDays();

        center.add(yearBox);
        center.add(monthBox);
        center.add(dayBox);

        add(center, BorderLayout.CENTER);

        // Buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnBack = new JButton("Back");
        btnSimulate = new JButton("Simulate");

        bottom.add(btnBack);
        bottom.add(btnSimulate);
        add(bottom, BorderLayout.SOUTH);

        // Events
        yearBox.addActionListener(e -> updateDays());
        monthBox.addActionListener(e -> updateDays());






    }

    private void populateYears() {
        int currentYear = AppMediator.getToday().getYear();
        for (int y = currentYear ; y <= currentYear + 10; y++) {
            yearBox.addItem(y);
        }
        yearBox.setSelectedItem(currentYear);
    }

    private void populateMonths() {
        for (int m = 1; m <= 12; m++) {
            monthBox.addItem(m);
        }
        monthBox.setSelectedItem(LocalDate.now().getMonthValue());
    }

    private void updateDays() {
        int year = (Integer) yearBox.getSelectedItem();
        int month = (Integer) monthBox.getSelectedItem();

        int maxDays = YearMonth.of(year, month).lengthOfMonth();
        Integer selectedDay = (Integer) dayBox.getSelectedItem();

        dayBox.removeAllItems();
        for (int d = 1; d <= maxDays; d++) {
            dayBox.addItem(d);
        }

        if (selectedDay != null && selectedDay <= maxDays) {
            dayBox.setSelectedItem(selectedDay);
        } else {
            dayBox.setSelectedItem(1);
        }
    }

    public JButton getBtnSimulate() {
        return btnSimulate;
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    public LocalDate getSelectedDate() {
        int year = (Integer) yearBox.getSelectedItem();
        int month = (Integer) monthBox.getSelectedItem();
        int day = (Integer) dayBox.getSelectedItem();

        return LocalDate.of(year, month, day);
    }
}
