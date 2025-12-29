package Entities.Transactions;

import Entities.Accounts.BankAcount;
import Entities.Accounts.Statements.Statement;
import Managers.StatementManager;
import swinglab.View.AppMediator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Deposit extends Transaction {

    private BankAcount sourceAccount;

    public Deposit(LocalDateTime timestamp, double amount,
                   String reason, String executorId, TransactionStatus status,
                   BankAcount sourceAccount) {
        super(timestamp, amount, reason, executorId, status);
        this.sourceAccount = sourceAccount;
    }

    public BankAcount getSourceAccount() { return sourceAccount; }

    @Override
    public String getType() { return "DEPOSIT"; }

    @Override
    protected void createStatement(BankAcount Source, BankAcount Target) {
        String[] ibansInvolved = {Source.getIBAN(),""};
        double[] remainingBalances = {Source.getAccountBalance(),0.0};
        Statement accountStatements = new Statement(super.getTransactionId(), AppMediator.getToday().atTime(LocalTime.now()),getAmount(),  remainingBalances,"DEPOSIT: "+getReason(),ibansInvolved,getTransactionId(),0.0,getType());
        Source.addStatements(accountStatements);
        StatementManager.getInstance().createStatement(accountStatements);
    }


    @Override
    public void Transact() {
        sourceAccount.setAccountBalance(sourceAccount.getAccountBalance() + getAmount());
        createStatement(sourceAccount,null);
        setStatus(TransactionStatus.COMPLETED);
    }


}
