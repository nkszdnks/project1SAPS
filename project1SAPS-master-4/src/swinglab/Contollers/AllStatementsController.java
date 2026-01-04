package swinglab.Contollers;

import Entities.Accounts.Statements.Statement;
import Entities.Users.Bills;
import Managers.BillManager;
import Managers.StatementManager;
import swinglab.View.AllStatementsPanel;
import swinglab.View.AppMediator;
import swinglab.View.ViewAllBillsPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllStatementsController implements ActionListener {

    private static AllStatementsController instance;
    private AllStatementsPanel view;
    private ViewAllBillsPanel billsPanel;

    public static AllStatementsController getInstance() {
        if (instance == null) {
            instance = new AllStatementsController();
        }
        return instance;
    }

    private AllStatementsController() {
    }

    public void setView(AllStatementsPanel view) {
        this.view = view;

        view.details.addActionListener(this);
        view.clearSel.addActionListener(this);
        view.closePan.addActionListener(this);


    }
    public void setViewBills(ViewAllBillsPanel view) {
        this.billsPanel = view;

        view.closePan.addActionListener(this);


    }

    public void loadStatements() {
        view.clearTable();

        for (Statement t : StatementManager.getInstance().getStatements()) {
            view.addStatementRow(t);
        }
    }
    public void loadBills() {
        billsPanel.clearTable();

        for (Bills t : BillManager.getInstance().getAllBills()) {
            billsPanel.addBillRow(t);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {

            case "View Details":
                view.showSelectedRowDetails();
                break;

            case "Clear Selection":
                view.clearSelection();
                break;

            case "Close":
                AppMediator.getCardLayout().show(
                        AppMediator.getCards(),
                        "adminDashboard"
                );
                break;

            default:
                System.out.println("Unhandled action: " + cmd);
        }
    }
}
