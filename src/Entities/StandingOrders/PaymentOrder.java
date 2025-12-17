package Entities.StandingOrders;

import java.time.LocalDate;

public class PaymentOrder extends StandingOrder {

    private String RFCode;
    private double amount;
    private LocalDate expirationDate;

    public PaymentOrder(int orderId, String title, String description, FrequencyType frequency,
                        LocalDate startDate, LocalDate endDate, double maxAmount,
                        OrderStatus status, String RFCode, double amount,String chargeIBAN,LocalDate expirationDate) {

        super(orderId, title, description, frequency, startDate, endDate, maxAmount, status,chargeIBAN);
        this.RFCode = RFCode;
        this.amount = amount;
        this.expirationDate = expirationDate;
    }

    public String getRFCode() { return RFCode; }
    public double getAmount() { return amount; }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String getType() {
        
        return "PAYMENT_ORDER"; 
    }

    @Override
    public String marshal() {
        return marshalBase() +
                ",type:PAYMENT_ORDER" +
                ",RFCode:" + RFCode +
                ",amount:" + amount +
                ",sourceIBAN:" + getChargeIBAN()+
                ",expirationDate:"+ expirationDate;
    }
}
