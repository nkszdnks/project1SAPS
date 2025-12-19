package Entities.StandingOrders;

import Entities.Transactions.Payment;
import Entities.Transactions.Requests.PaymentRequest;
import Entities.Transactions.Requests.TransactionRequest;
import Entities.Transactions.Requests.TransferRequest;
import Entities.Transactions.TransactionStatus;
import Entities.Users.Bills;
import Entities.checks.*;
import Managers.BillManager;
import Managers.TransactionManager;
import swinglab.AppMediator;

import java.time.LocalDate;
import java.time.LocalTime;

import static Entities.Transactions.Requests.TransferRequest.Rail.LOCAL;

public class PaymentOrder extends StandingOrder {

    private String RFCode;


    public PaymentOrder(String executorID, String orderId, String title, String description,
                        LocalDate startDate, LocalDate endDate, double maxAmount,
                        OrderStatus status, String RFCode, String chargeIBAN,  int attempts,double executionFee) {

        super(executorID,orderId, title, description,  startDate, endDate, maxAmount, status,chargeIBAN,attempts,executionFee);
        this.RFCode = RFCode;
    }

    public String getRFCode() { return RFCode; }


    @Override
    public void computeNextExecutionDate(LocalDate today) {
        Bills bill = BillManager.getInstance().findBill(RFCode);
        if(bill == null){
            setExecutionDate(LocalDate.MAX);
        }
        else {
            setExecutionDate(!today.isBefore(bill.getDueDate().minusDays(3-getAttempts())) ? today : bill.getDueDate().minusDays(3-getAttempts()));
            if(getExecutionDate().isAfter(bill.getDueDate())){
                status = OrderStatus.CANCELED;
            }
        }
    }

    @Override
    public String getType() { return "PAYMENT_ORDER"; }

    @Override
    public boolean executeOrder() {
        TransactionCheck checks;

        var RfCode = new RfCodeCheck();
        var balance = new BalanceCheck();
        var daily = new DailyLimitCheck();

        RfCode.setNext(balance);
        balance.setNext(daily);

        checks =RfCode;


        PaymentRequest paymentRequest = new PaymentRequest(getChargeIBAN(),RFCode,executorID);

            try {
                checks.handle(paymentRequest);
            } catch (IllegalStateException e) {
                setFailureReason(e.getMessage());
                attempts++;
                return false;
            }

//        // 2) Strategy: fee computation
//        FeeStrategy feeStrategy = FeeStrategyFactory.getStrategyFor(req);
//        double fee = feeStrategy.computeFee(req);
            double fee = executionFee;
            // ⭐ Use your TransferBuilder with flow interface
            Payment payment = new Payment("DefaultId", AppMediator.getToday().atTime(LocalTime.now()),paymentRequest.getAmount(),paymentRequest.getReason(),paymentRequest.getExecutorID(), TransactionStatus.PENDING,paymentRequest.getFromIban(),paymentRequest.getRfCode());
            TransactionManager.getInstance().Transact(payment);
            if(payment.getStatus() == TransactionStatus.FAILED){
                attempts++;
                return false;
            }
            // 3) Mock execution: just compute totals and return a message
            double debited = paymentRequest.getAmount() + fee;
            setAttempts(0);
            return true;
    }

    @Override
    public double getAmount() {
        Bills bill = BillManager.getInstance().findBill(RFCode);
        return bill!=null? bill.getAmount() : 0;
    }

    @Override
    public String marshal() {
        return marshalBase() +
                ",type:PAYMENT_ORDER" +
                ",RFCode:" + RFCode +
                ",sourceIBAN:" + getChargeIBAN();
    }
}
