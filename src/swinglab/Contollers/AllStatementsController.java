package swinglab.Contollers;

import Entities.Accounts.Statements.Statement;
import Entities.Transactions.Transaction;
import Managers.StatementManager;
import Managers.TransactionManager;
import swinglab.AllStatementsPanel;
import swinglab.AppMediator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllStatementsController implements ActionListener {

    private static AllStatementsController instance;
    private AllStatementsPanel view;

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

    public void loadStatements() {
        view.clearTable();

        for (Statement t : StatementManager.getInstance().getStatements()) {
            view.addStatementRow(t);
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
