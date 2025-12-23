package Entities.Transactions.Rails;


import Entities.Transactions.Payment;
import Entities.Transactions.Requests.PaymentRequest;
import Entities.Transactions.Requests.TransactionRequest;
import Entities.Transactions.TransactionStatus;
import Entities.checks.*;
import Managers.TransactionManager;
import swinglab.AppMediator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PaymentRail {
    private TransactionCheck checks;
    private String Message = "";

    public PaymentRail() {
        var iban = new IbanFormatCheck();
        var RfCode = new RfCodeCheck();
        var balance = new BalanceCheck();
        var daily = new DailyLimitCheck();

        iban.setNext(RfCode);
        RfCode.setNext(balance);
        balance.setNext(daily);

        checks = iban;
    }

    public String getMessage() {
        return Message;
    }

    public Boolean execute(PaymentRequest req){
        // 1) Chain of Responsibility: validations

        try {
            checks.handle(req);
        } catch (IllegalStateException e) {
            Message = "Payment failed. Reason:"+ e.getMessage();
            return false;
        }

//        // 2) Strategy: fee computation
//        FeeStrategy feeStrategy = FeeStrategyFactory.getStrategyFor(req);
//        double fee = feeStrategy.computeFee(req);
        double fee = req.getFee()==0.0?0.0:req.getFee();
        // ⭐ Use your TransferBuilder with flow interface
        Payment payment = new Payment("DefaultId", AppMediator.getToday().atTime(LocalTime.now()),req.getAmount(),req.getReason(),req.getExecutorID(), TransactionStatus.PENDING,req.getFromIban(),((PaymentRequest)req).getRfCode(),fee);
        TransactionManager.getInstance().Transact(payment);
        if(payment.getStatus() == TransactionStatus.FAILED){
            return false;
        }
        // 3) Mock execution: just compute totals and return a message
        double debited = req.getAmount() + fee;
        Message = "Transfer executed successfully: rail="
                + "   Amount=" + req.getAmount()
                + "   Fee=" + String.format("%.2f", fee)
                + "   Debited=" + String.format("%.2f", debited);
        return true;
    }
}