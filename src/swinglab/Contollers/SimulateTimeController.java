package swinglab.Contollers;

import Managers.BillManager;
import Managers.StandingOrderManager;
import swinglab.AppMediator;
import swinglab.View.SimulateTimePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class SimulateTimeController implements ActionListener {

    private static SimulateTimeController instance;
    private static SimulateTimePanel view;

    public SimulateTimeController(SimulateTimePanel view) {
        this.view = view;
        view.getBtnSimulate().addActionListener(this);
        view.getBtnBack().addActionListener(this);
    }



    public void simulateTo(LocalDate targetDate) {

        LocalDate today = AppMediator.getToday();

        if (targetDate.isBefore(today)) {
            JOptionPane.showMessageDialog(
                    view,
                    "You cannot simulate to past date",
                    "Simulation Failed ",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }


        // Simulate day-by-day
        while (today.isBefore(targetDate)) {
            today = today.plusDays(1);

            AppMediator.setToday(today);
            BillManager.getInstance().restoreEachDay(today);
            StandingOrderManager.getInstance().executeOrders();

            // Restore / process daily operations
//            AppMediator.getBank().restoreEachDay(today);
//            AppMediator.getBank().processStandingOrders(today);
//            AppMediator.getBank().processBills(today);
        }
        JOptionPane.showMessageDialog(
                view,
                "Time simulated to: " + AppMediator.getToday().toString(),
                "Simulation Complete",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {
            case "Simulate":
                simulateTo(view.getSelectedDate());
                break;

            case "Back":
                AppMediator.getCardLayout().show(AppMediator.getCards(), "adminDashboard");
                break;
        }
    }
}
