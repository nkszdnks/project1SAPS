package Entities.Transactions.Builders;

import Entities.Transactions.Transaction;
import Entities.Transactions.TransactionStatus;

import java.time.LocalDateTime;

public abstract class TransactionBuilder {

    protected LocalDateTime timestamp;
    protected double amount;
    protected String reason;
    protected String executorID;
    protected TransactionStatus status;


    public TransactionBuilder setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
    public TransactionBuilder setAmount(double amount) {
        this.amount = amount;
        return this;
    }
    public TransactionBuilder setReason(String reason) {
        this.reason = reason;
        return this;
    }
    public TransactionBuilder setExecutorID(String executorID) {
        this.executorID = executorID;
        return this;
    }
    public TransactionBuilder setStatus(TransactionStatus status) {
        this.status = status;
        return this;
    }
    public abstract Transaction build();


}
