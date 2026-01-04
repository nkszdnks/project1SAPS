package swinglab.Contollers;

import Entities.Accounts.Statements.FailedOrderStatement;
import Entities.Users.Customer;
import Managers.StandingOrderManager;
import swinglab.View.AppMediator;
import swinglab.FailedOrdersPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FailedOrdersController implements ActionListener {

    private static FailedOrdersController instance;
    private FailedOrdersPanel view;
    private Customer model;

    private FailedOrdersController() {
    }

    public static FailedOrdersController getInstance() {
        if (instance == null) {
            instance = new FailedOrdersController();
        }
        return instance;
    }

    public void setModel(Customer model) {
        this.model = model;
        loadFailedOrders();
    }

    public void setView(FailedOrdersPanel view) {
        this.view = view;

        // Attach listeners
        view.getBtnBack().addActionListener(this);
    }

    private void loadFailedOrders() {
        view.clearTable();

        for (FailedOrderStatement f :
                StandingOrderManager.getInstance()
                        .getMyFailed(model)) {

            view.addFailedOrderRow(
                    f.getTitle(),
                    f.getOrderType(),
                    f.getAmount(),
                    String.valueOf(f.getTimestamp()),
                    f.getIbansInvolved()[0],
                    f.getFailureReason(),
                    f.getAttempts()
            );
        }
    }
    public  void loadAllFailedOrders() {
        view.clearTable();

        for (FailedOrderStatement f :
                StandingOrderManager.getInstance()
                        .getFailed()) {

            view.addFailedOrderRow(
                    f.getTitle(),
                    f.getOrderType(),
                    f.getAmount(),
                    String.valueOf(f.getTimestamp()),
                    f.getIbansInvolved()[0],
                    f.getFailureReason(),
                    f.getAttempts()
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {

            case "Back":
                AppMediator.getCardLayout()
                        .show(AppMediator.getCards(), "activeStandingOrders");
                break;

            default:
                System.out.println("Unhandled action: " + cmd);
        }
    }
}
