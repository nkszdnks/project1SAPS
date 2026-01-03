package swinglab.Contollers;

import Commands.ScheduledTransferCommand;
import Entities.StandingOrders.StandingOrder;
import Entities.Transactions.Requests.TransferRequest;
import Entities.Users.Customer;
import Managers.StandingOrderManager;
import swinglab.View.*;
import swinglab.Observers.StandingOrderObserver;
import swinglab.View.StandingOrderDetailsDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class ScheduledTransfersController implements ActionListener, StandingOrderObserver {
    private static ScheduledTransfersController instance;
    private ScheduledTransfersPanel view;
    private Customer model;


    public static ScheduledTransfersController getInstance() {
        if(instance == null){
            instance = new ScheduledTransfersController();
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

    private ScheduledTransfersController() {

        // Load table data initially
    }

    public void setView(ScheduledTransfersPanel view) {
        this.view = view;

        // Attach button listeners

        view.getBtnClose().addActionListener(this);
        view.getBtnDelete().addActionListener(this);

    }





    private void loadOrders() {
        view.clearTable();
        ArrayList<ScheduledTransferCommand> orders = new ArrayList<>();
        orders.addAll(StandingOrderManager.getInstance().getScheduledTransfers());

        for (ScheduledTransferCommand s :orders)
        {
         //   s.attach(this);
         //   s.computeNextExecutionDate(AppMediator.getToday());
            view.addAccountRow(
                    s.getreq(),
                    String.valueOf(s.getreq().getAmount()),
                    s.getreq().getReason(),
                    s.getreq().getToIban(),
              //      String.valueOf(s.getStartDate()),
              //      String.valueOf(s.getEndDate()),
                    String.valueOf(s.getScheduledDate().equals(LocalDate.MAX)?"NO ISSUED BILL FOUND":s.getScheduledDate())
                //    s.getChargeIBAN(),
                //    String.valueOf(s.getStatus())
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {

            case "Delete":
                deleteOrder();
                loadOrders();
                break;

            case "Clear Selection":
                view.clearSelection();
                break;

            case "Close":
                if(model==null){
                    AppMediator.getCardLayout().show(AppMediator.getCards(),"adminDashboard");
                    break;
                }
                AppMediator.getCardLayout().show(AppMediator.getCards(), "dashboard");
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

    public void deleteOrder() {

        int viewRow = view.getTable().getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a row first.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.getTable().convertRowIndexToModel(viewRow);

        StandingOrderManager.getInstance().deleteScheduledTransferByRequest((TransferRequest) view.getModel().getValueAt(modelRow, 0));

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
