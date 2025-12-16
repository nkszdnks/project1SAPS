package Entities.Transactions.Builders;

import Entities.Transactions.Transaction;
import Entities.Transactions.Transfer;

public class TransferBuilder extends TransactionBuilder {
    private String sourceIBAN;
    private String targetIBAN;
    private float bankFee;
    private String type;


    // Generate all the setters
    public  TransferBuilder setSourceIBAN(String sourceIBAN) {
        this.sourceIBAN = sourceIBAN;
        return this;

    }

    public  TransferBuilder setTargetIBAN(String targetIBAN) {
        this.targetIBAN = targetIBAN;
        return this;
    }

    public TransferBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public TransferBuilder setBankFee(float bankFee) {
        this.bankFee = bankFee;
        return this;
    }

    @Override
    public Transaction build() {
        Transfer transfer = new Transfer(transactionId,timestamp,amount,reason,executorID,status,sourceIBAN,targetIBAN,bankFee,type);
        return transfer;
    }
}
