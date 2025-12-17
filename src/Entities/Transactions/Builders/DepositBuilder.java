//package Entities.Transactions.Builders;
//
//import Entities.Transactions.Deposit;
//import Entities.Transactions.Transaction;
//
//import java.time.LocalDateTime;
//
//public class DepositBuilder extends TransactionBuilder{
//private String sourceIBAN;
//
//    public DepositBuilder setSourceIBAN(String sourceIBAN) {
//        this.sourceIBAN = sourceIBAN;
//        return this;
//    }
//
//    @Override
//    public Transaction build() {
//
//        if (timestamp == null) {
//            timestamp = LocalDateTime.now();//Default Timestamp assuming the user didn't set a timestamp
//        }
//        if (reason==null){
//            reason ="-";
//        }
//        return new Deposit(
//
//                transactionId,
//                timestamp,
//                amount,
//                reason,
//                executorID,
//                status,
//                sourceIBAN
//        );
//    }
//}
//
//
//
//
