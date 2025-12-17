package Entities.Transactions;


import Entities.Accounts.BankAcount;

import java.time.LocalDateTime;

public abstract class Transaction {

    private String transactionId;
    private LocalDateTime timestamp;
    private double amount;
    private String reason;
    private String executorID;
    private TransactionStatus status;

    public Transaction(String transactionId, LocalDateTime timestamp, double amount,
                       String reason, String executorID, TransactionStatus status) {
        this.transactionId = transactionId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.reason = reason;
        this.executorID = executorID;
        this.status = status;
    }

    public String getTransactionId() { return transactionId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getAmount() { return amount; }
    public String getReason() { return reason; }
    public String getExecutorID() { return executorID; }
    public TransactionStatus getStatus() { return status; }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public abstract String getType();

    protected abstract void createStatement(BankAcount Source, BankAcount Target);

    public abstract void Transact();
}
