package Entities.StandingOrders;

import Entities.Transactions.Builders.TransferBuilder;
import Entities.Transactions.Requests.TransferRequest;
import Entities.Transactions.Transaction;
import Entities.Transactions.TransactionStatus;
import Entities.checks.BalanceCheck;
import Entities.checks.DailyLimitCheck;
import Entities.checks.TransactionCheck;
import Managers.TransactionManager;
import swinglab.AppMediator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static Entities.Transactions.Requests.TransferRequest.Rail.LOCAL;

public class TransferOrder extends StandingOrder {

    private String targetIBAN;
    private double amount;
    private int frequencyMonths;
    private int firstExecutionDay;

    public TransferOrder(String executorID, String orderId, String title, String description,int firstExecutionDay, int frequency,
                         LocalDate startDate, LocalDate endDate, double maxAmount,
                         OrderStatus status, String targetIBAN, double amount, String chargeIBAN, int attempts,double executionFee) {

        super(executorID,orderId, title, description, startDate, endDate, maxAmount, status,chargeIBAN,attempts,executionFee);
        this.targetIBAN = targetIBAN;
        this.amount = amount;
        this.frequencyMonths = frequency;
        this.firstExecutionDay = firstExecutionDay;
    }


    public String getTargetIBAN() { return targetIBAN; }
    public double getAmount() { return amount; }

//    @Override
//    public void computeNextExecutionDate(LocalDate today) {
//
//        setExecutionDate(firstExecutionDay.plusDays(attempts));
//
//
//        while (getExecutionDate().isBefore(today)) {
//            setExecutionDate(getExecutionDate().plusMonths(frequencyMonths!=0?frequencyMonths: firstExecutionDay.getDayOfYear()));
//        }
//
//        }
@Override
public void computeNextExecutionDate(LocalDate today) {
        if(today.isBefore(startDate)) {
            setExecutionDate(LocalDate.MAX);
            return;
        }
    LocalDate next = LocalDate.of(
            today.getYear(),
            today.getMonth(),
            firstExecutionDay
    );
        next = next.plusDays(attempts);

    if (next.isBefore(today)) {
        next = next.plusMonths(frequencyMonths);
    }

    setExecutionDate(next);
}





    @Override
    public String getType() { return "TRANSFER_ORDER"; }

    @Override
    public boolean executeOrder() {
        TransactionCheck checks;

        var balance = new BalanceCheck();
        var daily = new DailyLimitCheck();

        balance.setNext(daily);

        checks = balance;
        TransferRequest transferRequest = new TransferRequest(getChargeIBAN(),targetIBAN,amount,LOCAL,executorID,description);
        try {
            checks.handle(transferRequest);
        } catch (IllegalStateException e) {
            setFailureReason(e.getMessage());
            attempts++;
            return false;
        }
        double fee = executionFee;
        // ⭐ Use your TransferBuilder with flow interface
        Transaction transfer = new TransferBuilder()
                .setSourceIBAN(transferRequest.getFromIban())
                .setBankFee(0)
                .setType("INTRA")
                .setTargetIBAN(transferRequest.getToIban())
                .setAmount(transferRequest.getAmount())
                .setReason(transferRequest.getReason())
                .setExecutorID(AppMediator.getUser().getUserId())
                .setStatus(TransactionStatus.PENDING)
                .setTimestamp(AppMediator.getToday().atTime(LocalTime.now()))
                .setTransactionId("DefaultID....")
                .build();
        TransactionManager.getInstance().Transact(transfer);
        if(transfer.getStatus() == TransactionStatus.FAILED){
            setFailureReason("Destination IBAN: "+getTargetIBAN()+" not found");
            attempts++;
            return false;
        }
        // 3) Mock execution: just compute totals and return a message
        double debited = transferRequest.getAmount() + fee;
        setAttempts(0);
        return true;
        }




    @Override
    public String marshal() {
        return marshalBase() +
                ",frequencyMonths:" + String.valueOf(frequencyMonths) +
                ",firstExecution:" + String.valueOf(firstExecutionDay) +
                ",type:TRANSFER_ORDER" +
                ",sourceIBAN:" + getChargeIBAN() +
                ",targetIBAN:" + targetIBAN +
                ",amount:" + amount;

    }
}
