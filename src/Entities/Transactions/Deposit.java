package Entities.Transactions;

import Entities.Accounts.BankAcount;

import java.time.LocalDateTime;

public class Deposit extends Transaction {

    private String sourceIBAN;

    public Deposit(String transactionId, LocalDateTime timestamp, double amount,
                   String reason, String executorId, TransactionStatus status,
                   String sourceIBAN) {
        super(transactionId, timestamp, amount, reason, executorId, status);
        this.sourceIBAN = sourceIBAN;
    }

    public String getSourceIBAN() { return sourceIBAN; }

    @Override
    public String getType() { return "DEPOSIT"; }

    @Override
    public void createStatement(BankAcount Source, BankAcount Target) {

    }


    @Override
    public void Transact() {

    }


}
