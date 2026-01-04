package Entities.Transactions.Builders;

import Entities.Transactions.Transaction;
import Entities.Transactions.Withdrawal;

import java.time.LocalDateTime;

public class WithdrawalBuilder extends TransactionBuilder {
    private String sourceIBAN;

    public WithdrawalBuilder setSourceIBAN(String sourceIBAN) {
        this.sourceIBAN = sourceIBAN;
        return this;
    }

    @Override
    public Transaction build() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
        if (reason==null){
            reason ="-";
        }
        return new Withdrawal(
                timestamp,
                amount,
                reason,
                executorID,
                status,
                sourceIBAN
        );
    }
}