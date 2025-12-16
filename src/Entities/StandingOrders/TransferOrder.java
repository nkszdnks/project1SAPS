package Entities.StandingOrders;

import java.time.LocalDate;

public class TransferOrder extends StandingOrder {

    private String targetIBAN;
    private double amount;

    public TransferOrder(int orderId, String title, String description, FrequencyType frequency,
                         LocalDate startDate, LocalDate endDate, double maxAmount,
                         OrderStatus status, String targetIBAN, double amount, String chargeIBAN) {

        super(orderId, title, description, frequency, startDate, endDate, maxAmount, status,chargeIBAN);
        this.targetIBAN = targetIBAN;
        this.amount = amount;
    }


    public String getTargetIBAN() { return targetIBAN; }
    public double getAmount() { return amount; }

    @Override
    public String getType() { return "TRANSFER_ORDER"; }

    @Override
    public String marshal() {
        return marshalBase() +
                ",type:TRANSFER_ORDER" +
                ",sourceIBAN:" + getChargeIBAN() +
                ",targetIBAN:" + targetIBAN +
                ",amount:" + amount;
    }
}
