package Entities.checks;

import Entities.Transactions.Requests.TransactionRequest;

public interface TransactionCheck {
    void setNext(TransactionCheck next);
    void handle(TransactionRequest req) throws IllegalStateException;
}