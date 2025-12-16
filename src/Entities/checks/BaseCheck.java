package Entities.checks;

import Entities.Transactions.Requests.TransactionRequest;

public abstract class BaseCheck implements TransactionCheck {
    private TransactionCheck next;
    @Override
    public void setNext(TransactionCheck n){ this.next = n; }

    @Override
    public void handle(TransactionRequest req){
        if (!apply(req)) throw new IllegalStateException(failMessage());
        if (next != null) next.handle(req);
    }
    protected abstract boolean apply(TransactionRequest req);
    protected abstract String failMessage();
}