package swinglab.Contollers;

import Entities.Accounts.BankAcount;
import Entities.Accounts.Statements.Statement;
import swinglab.AppMediator;
import swinglab.TransactionHistoryPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TransactionHistoryController implements ActionListener {

    private static TransactionHistoryController instance;
    private TransactionHistoryPanel view;
    private  BankAcount model;   // your model

    private TransactionHistoryController() {}
    public static TransactionHistoryController getInstance() {
        if (instance == null) {
            instance = new TransactionHistoryController();
        }
        return instance;

    }

    public BankAcount getModel() {
        return model;
    }

    public void setView(TransactionHistoryPanel view) {
        this.view = view;
        view.closePan.addActionListener(this);
    }

    public void setModel(BankAcount model) {
        this.model = model;

        loadHistoryForAccount();
    }

    /** Loads all statements for a given IBAN and fills the table. */
    public void loadHistoryForAccount() {
        List<Statement> statements = model.getAccountStatements();
        view.fillTable(statements);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.closePan)
            AppMediator.getCardLayout().show(AppMediator.getCards(),"accounts");
    }
}
