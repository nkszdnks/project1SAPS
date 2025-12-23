package Entities.Transactions;

import Entities.Accounts.BankAcount;
import Entities.Accounts.Statements.Statement;
import Managers.AccountManager;
import Managers.StatementManager;

import java.time.LocalDateTime;

public class Withdrawal extends Transaction {

    private String sourceIBAN;

    public Withdrawal(String transactionId, LocalDateTime timestamp, double amount,
                      String reason, String executorID, TransactionStatus status,
                      String sourceIBAN) {
        super(transactionId, timestamp, amount, reason, executorID, status);
        this.sourceIBAN = sourceIBAN;
    }

    public String getSourceIBAN() { return sourceIBAN; }

    @Override
    public String getType() { return "WITHDRAWAL"; }

    @Override
    protected void createStatement(BankAcount Source, BankAcount Target) {
        String[] ibansInvolved = {sourceIBAN,""};
        double[] remainingBalances = {Source.getAccountBalance(),0.0};
        Statement accountStatements = new Statement(super.getTransactionId(),getTimestamp(),getAmount(),  remainingBalances,getReason(),ibansInvolved,getTransactionId(),0.0);
        Source.addStatements(accountStatements);
        StatementManager.getInstance().createStatement(accountStatements);
    }


    @Override
    public void Transact() {
        BankAcount source = AccountManager.getInstance().findAccountByIBAN(sourceIBAN);
        source.setAccountBalance(source.getAccountBalance() -super.getAmount());
        createStatement(source,null);
        setStatus(TransactionStatus.COMPLETED);
    }

}
