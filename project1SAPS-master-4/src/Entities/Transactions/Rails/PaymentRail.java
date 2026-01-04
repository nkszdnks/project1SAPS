package Entities.Transactions.Rails;


import Entities.Transactions.Payment;
import Entities.Transactions.Requests.PaymentRequest;
import Entities.Transactions.TransactionStatus;
import Entities.checks.*;
import Managers.TransactionManager;
import swinglab.View.AppMediator;

import java.time.LocalTime;

public class PaymentRail {
    private TransactionCheck checks;
    private String Message = "";

    public PaymentRail() {
        var RfCode = new RfCodeCheck();
        var balance = new BalanceCheck();
        var daily = new DailyLimitCheck();

        RfCode.setNext(balance);
        balance.setNext(daily);

        checks = RfCode;
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
        Payment payment = new Payment(AppMediator.getToday().atTime(LocalTime.now()),req.getAmount(),req.getReason(),req.getExecutorID(), TransactionStatus.PENDING,req.getFromIban(),((PaymentRequest)req).getRfCode(),fee);
        TransactionManager.getInstance().Transact(payment);
        if(payment.getStatus() == TransactionStatus.FAILED){
            return false;
        }
        // 3) Mock execution: just compute totals and return a message
        double debited = req.getAmount() + fee;
        Message = "Payment executed successfully: "+"\n"
                + "   Amount=" + req.getAmount()+"\n"
                + "   Fee=" + String.format("%.2f", fee)+"\n"
                + "   Debited=" + String.format("%.2f", debited);
        return true;
    }
}