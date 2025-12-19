package Entities.Transactions.Rails;


import Entities.Transactions.Transaction;
import Entities.Transactions.TransactionStatus;
import Entities.Transactions.Builders.TransferBuilder;
import Entities.Transactions.Requests.TransferRequest;
import Entities.checks.BalanceCheck;
import Entities.checks.DailyLimitCheck;
import Entities.checks.IbanFormatCheck;
import Entities.checks.TransactionCheck;

import Managers.TransactionManager;
import swinglab.AppMediator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TransferRail {
    private TransactionCheck checks;
    
    public TransferRail() {
        var iban = new IbanFormatCheck();
        var balance = new BalanceCheck();
        var daily = new DailyLimitCheck();
        iban.setNext(balance);
        balance.setNext(daily);
        
        checks = iban;
    }

    public String execute(TransferRequest req){
        // 1) Chain of Responsibility: validations
        
        try {
        	checks.handle(req);
        } catch (IllegalStateException e) {
           return "Transfer failed. Reason:"+ e.getMessage();
        }

//        // 2) Strategy: fee computation
//        FeeStrategy feeStrategy = FeeStrategyFactory.getStrategyFor(req);
//        double fee = feeStrategy.computeFee(req);
        double fee = 0.0;
        // ⭐ Use your TransferBuilder with flow interface
        Transaction transfer = new TransferBuilder()
                .setSourceIBAN(req.getFromIban())
                .setBankFee(0)
                .setType("INTRA")
                .setTargetIBAN(req.getToIban())
                .setAmount(req.getAmount())
                .setReason(req.getReason())
                .setExecutorID(AppMediator.getUser().getUserId())
                .setStatus(TransactionStatus.PENDING)
                .setTimestamp(AppMediator.getToday().atTime(LocalTime.now()))
                .setTransactionId("DefaultID....")
                .build();
        TransactionManager.getInstance().Transact(transfer);
        if(transfer.getStatus() == TransactionStatus.FAILED){
            return "Transfer failed!!!";
        }
        // 3) Mock execution: just compute totals and return a message
        double debited = req.getAmount() + fee;
        return "Transfer executed successfully: rail=" + req.getRail()
                + " amount=" + req.getAmount()
                + " fee=" + String.format("%.2f", fee)
                + " debited=" + String.format("%.2f", debited);
    }
}