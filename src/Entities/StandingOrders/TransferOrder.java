package Entities.StandingOrders;

import Entities.Transactions.Rails.TransferRail;
import Entities.Transactions.Requests.TransferRequest;

import java.time.LocalDate;
import java.util.HashMap;

public class TransferOrder extends StandingOrder {

    private TransferRequest.Rail rail;
    private String targetIBAN;
    private double amount;
    private int frequencyMonths;
    private int firstExecutionDay;
    private HashMap<String, String> transferDetails = new HashMap<>();

    public void setDetails(HashMap<String, String> Details) {
        this.transferDetails = Details;
    }

    public TransferOrder(String executorID, String orderId, String title, String description, int firstExecutionDay, int frequency,
                         LocalDate startDate, LocalDate endDate, double maxAmount,
                         OrderStatus status, String targetIBAN, double amount, String chargeIBAN, int attempts, double executionFee, TransferRequest.Rail rail) {

        super(executorID, orderId, title, description, startDate, endDate, maxAmount, status, chargeIBAN, attempts, executionFee);
        this.targetIBAN = targetIBAN;
        this.amount = amount;
        this.frequencyMonths = frequency;
        this.firstExecutionDay = firstExecutionDay;
        this.rail = rail;
    }


    public String getTargetIBAN() {
        return targetIBAN;
    }

    public double getAmount() {
        return amount;
    }


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
        if (today.isBefore(startDate)) {
            today = startDate;
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
    public String getType() {
        return "TRANSFER_ORDER";
    }

    @Override
    public boolean executeOrder() {
        if (amount > maxAmount) {
            setFailureReason("Amount is greater than max amount");
            return false;
        }
        TransferRail transferRail = new TransferRail();


        TransferRequest transferRequest = new TransferRequest(getChargeIBAN(), targetIBAN, amount, rail, executorID, description, executionFee);

        Boolean isSuccessfull = transferRail.execute(transferRequest, transferDetails);
        if (!isSuccessfull) {
            setFailureReason(transferRail.getMessage());
            attempts++;
        } else {
            setAttempts(0);
        }
        return isSuccessfull;

    }





    @Override
    public String marshal() {

        StringBuilder sb = new StringBuilder();

        // Base fields (StandingOrder)
        sb.append(marshalBase());

        // TransferOrder common fields
        sb.append(",frequencyMonths:").append(frequencyMonths);
        sb.append(",firstExecution:").append(firstExecutionDay);
        sb.append(",type:TRANSFER_ORDER");
        sb.append(",sourceIBAN:").append(getChargeIBAN());
        sb.append(",targetIBAN:").append(targetIBAN);
        sb.append(",amount:").append(amount);
        sb.append(",rail:").append(rail);

        // Rail-specific details
        if (transferDetails != null && !transferDetails.isEmpty()) {
            for (HashMap.Entry<String, String> entry : transferDetails.entrySet()) {
                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                    sb.append(",")
                            .append(entry.getKey())
                            .append(":")
                            .append(entry.getValue());
                }
            }
        }

        return sb.toString();
    }

}
