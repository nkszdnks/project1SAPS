package Entities.Transactions.Rails;

import Entities.Transactions.Requests.WithdrawlRequest;
import Entities.Transactions.TransactionStatus;
import Entities.Transactions.Withdrawal;
import Entities.checks.*;
import Managers.TransactionManager;

import java.time.LocalDateTime;

public class WithdrawlRail {
    private TransactionCheck checks;

    public WithdrawlRail() {
        var iban = new IbanFormatCheck();
        var balance = new BalanceCheck();
        var daily = new DailyLimitCheck();

        iban.setNext(balance);
        balance.setNext(daily);

        checks = iban;
    }

    public String execute(WithdrawlRequest req){
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
        Withdrawal withdrawal = new Withdrawal("DefaultId", LocalDateTime.now(),req.getAmount(),req.getReason(),req.getExecutorID(), TransactionStatus.PENDING,req.getFromIban());
        TransactionManager.getInstance().Transact(withdrawal);
        if(withdrawal.getStatus() == TransactionStatus.FAILED){
            return "Transfer failed!!!";
        }
        // 3) Mock execution: just compute totals and return a message
        double debited = req.getAmount() + fee;
        return "Withdrawal executed successfully: rail="
                + "   Amount=" + req.getAmount();
    }
}
