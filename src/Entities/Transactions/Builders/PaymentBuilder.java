package Entities.Transactions.Builders;

import Entities.Transactions.Payment;
import Entities.Transactions.Transaction;

import java.time.LocalDateTime;

public class PaymentBuilder extends TransactionBuilder {
    private String sourceIBAN;
    private String billRFCode;

    public PaymentBuilder setSourceIBAN(String sourceIBAN) {
        this.sourceIBAN = sourceIBAN;
        return this;
    }

    public PaymentBuilder setBillRFCode(String billRFCode) {
        this.billRFCode = billRFCode;
        return this;
    }

    @Override
    public Transaction build() {
        if (timestamp == null) {          // default to now 
            timestamp = LocalDateTime.now();
        }
        if (reason==null){
            reason ="-";
        }
        return new Payment(
                transactionId,
                timestamp,
                amount,
                reason,
                executorID,
                status,
                sourceIBAN,
                billRFCode
        );
    }
}