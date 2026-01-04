package swinglab.Contollers;

import Entities.StandingOrders.StandingOrder;
import Entities.Users.Customer;
import Managers.StandingOrderManager;
import swinglab.View.*;
import swinglab.Observers.StandingOrderObserver;
import swinglab.View.StandingOrderDetailsDialog;
import Entities.Users.UserRole;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class ActiveOrdersController implements ActionListener, StandingOrderObserver {
    private static ActiveOrdersController instance;
    private ActiveStandingOrdersPanel view;
    private Customer model;


    public static ActiveOrdersController getInstance() {
        if(instance == null){
            instance = new ActiveOrdersController();
            return instance;
        }
        return instance;
    }


    public void setModel(Customer model) {
        this.model = model;
        if(model != null) {
            loadOrders();
        }
    }

    private ActiveOrdersController() {

        // Load table data initially
    }

    public void setView(ActiveStandingOrdersPanel view) {
        this.view = view;

        // Attach button listeners

        view.getBtnClose().addActionListener(this);
        view.getBtnEdit().addActionListener(this);
        view.getBtnDelete().addActionListener(this);

    }





    private void loadOrders() {
        view.clearTable();
        ArrayList<StandingOrder> orders = new ArrayList<>();
        orders.addAll(StandingOrderManager.getInstance().getMyActive(model));
        orders.addAll(StandingOrderManager.getInstance().getMyExpired(model));

        for (StandingOrder s :orders)
        {
            s.attach(this);
            s.computeNextExecutionDate(AppMediator.getToday());
            view.addAccountRow(
                    s.getOrderId(),
                    s.getTitle(),
                    s.getType(),
                    String.valueOf(s.getStartDate()),
                    String.valueOf(s.getEndDate()),
                    String.valueOf(s.getExecutionDate().equals(LocalDate.MAX)?"NO ISSUED BILL FOUND":s.getExecutionDate()),
                    s.getChargeIBAN(),
                    String.valueOf(s.getStatus())
            );
        }
    }
    public void loadAllOrders(){
        view.clearTable();
        ArrayList<StandingOrder> orders = new ArrayList<>();
        orders.addAll(StandingOrderManager.getInstance().getActive());
        orders.addAll(StandingOrderManager.getInstance().getExpired());
        for (StandingOrder s : orders) {
            s.attach(this);
            s.computeNextExecutionDate(AppMediator.getToday());
            view.addAccountRow(
                    s.getOrderId(),
                    s.getTitle(),
                    s.getType(),
                    String.valueOf(s.getStartDate()),
                    String.valueOf(s.getEndDate()),
                    String.valueOf(s.getExecutionDate().equals(LocalDate.MAX)?"NO ISSUED BILL FOUND":s.getExecutionDate()),
                    s.getChargeIBAN(),
                    String.valueOf(s.getStatus())
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {

            case "View Details":
                showSelectedRowDetails();
                break;

            case "Clear Selection":
                view.clearSelection();
                break;

            case "Close":
                if(model==null){
                    String card = AppMediator.getUser().getRole() == UserRole.BUSINESS ? "businessDashboard"
                            : AppMediator.getUser().getRole() == UserRole.ADMIN    ? "adminDashboard"
                            : "dashboard";
                    AppMediator.getCardLayout().show(AppMediator.getCards(), card);
                    break;
                }
                AppMediator.goToHomeDashboard();

                break;

            case "Show Failed":
                if(model==null){
                    FailedOrdersController.getInstance().loadAllFailedOrders();
                }
                else{
                    FailedOrdersController.getInstance().setModel(model);
                }
                AppMediator.getCardLayout().show(AppMediator.getCards(), "failedOrders");
                break;


            default:
                System.out.println("Unhandled action: " + cmd);
        }
    }
    public void showSelectedRowDetails() {
        int viewRow = view.getTable().getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a row first.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.getTable().convertRowIndexToModel(viewRow);

        String orderID   = String.valueOf(view.getModel().getValueAt(modelRow, 0));
        StandingOrder s = StandingOrderManager.getInstance().findOrderByID(orderID);


        StandingOrderDetailsDialog standingOrderDetailsDialog = new StandingOrderDetailsDialog(
                SwingUtilities.getWindowAncestor(view),s);
        standingOrderDetailsDialog.setVisible(true);
    }


    @Override
    public void update(String orderID) {
        DefaultTableModel model =
                (DefaultTableModel) view.getTable().getModel();

        for (int row = 0; row < model.getRowCount(); row++) {

            Object value = model.getValueAt(row, 0); // Order ID column

            if (orderID.equals(value)) {
                StandingOrder s = StandingOrderManager.getInstance().findOrderByID(orderID);
                model.setValueAt(String.valueOf(s.getStatus()), row, 7); // Status column
                break;
            }
        }

    }
    }

