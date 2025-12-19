package Entities.StandingOrders;

import java.time.LocalDate;
import java.util.HashMap;

public abstract class StandingOrder {

    protected String orderId;
    protected String executorID;
    protected String title;
    protected String description;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected double maxAmount;
    protected OrderStatus status;
    protected int attempts=0;
    private String chargeIBAN;
    private LocalDate executionDate;
    protected double executionFee;
    protected double amount;
    private String failureReason;

    public StandingOrder(String executorID,String orderId, String title, String description, LocalDate startDate,
                         LocalDate endDate, double maxAmount, OrderStatus status,String chargeIBAN,int attempts,double executionFee) {

        this.executorID =  executorID;
        this.orderId = orderId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxAmount = maxAmount;
        this.status = status;
        this.attempts = attempts;
        this.chargeIBAN = chargeIBAN;
        this.executionFee =  executionFee;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public abstract void computeNextExecutionDate(LocalDate today);
    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public String getOrderId() { return orderId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public double getMaxAmount() { return maxAmount; }
    public OrderStatus getStatus() { return status; }

    public int getAttempts() {
        return attempts;
    }

    public String getChargeIBAN() {
        return chargeIBAN;
    }

    public abstract String getType();
    public abstract boolean executeOrder();
    public abstract double getAmount();
    public  String getFailureReason() { return failureReason; }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // Key:value CSV marshal
    public String marshalBase() {
        return  "executorID:" + executorID +
                ",orderId:" + orderId +
                ",title:" + title +
                ",description:" + description +
                ",startDate:" + startDate +
                ",endDate:" + endDate +
                ",maxAmount:" + maxAmount +
                ",executionFee:"+executionFee+
                ",status:" + status+
                ",attempts:" + attempts;
    }
    public abstract String marshal();

    public static StandingOrder unmarshal(String csvLine) {

        String[] data = csvLine.split(",", -1);
        HashMap<String, String> map = new HashMap<>();

        for (String c : data) {
            String[] kv = c.split(":", 2);
            if (kv.length == 2) map.put(kv[0], kv[1]);
        }

        String type = map.get("type");

        // Base fields
        String orderId = map.get("orderId");
        String executorId = map.get("executorID");
        String title = map.get("title");
        String description = map.get("description");
        LocalDate start = LocalDate.parse(map.get("startDate"));
        LocalDate end = LocalDate.parse(map.get("endDate"));
        double maxAmount = Double.parseDouble(map.get("maxAmount"));
        OrderStatus status = OrderStatus.valueOf(map.get("status"));
        int attempts = Integer.parseInt(map.get("attempts"));
        double executionFee = Double.parseDouble(map.get("executionFee"));

        switch (type) {

            case "TRANSFER_ORDER":
                return new TransferOrder(
                        executorId,
                        orderId,
                        title,
                        description,
                        Integer.parseInt(map.get("firstExecution")),
                        Integer.parseInt(map.get("frequencyMonths")),
                        start,
                        end,
                        maxAmount,
                        status,
                        map.get("targetIBAN"),
                        Double.parseDouble(map.get("amount")),
                        map.get("sourceIBAN"),
                        attempts,
                        executionFee
                );

            case "PAYMENT_ORDER":
                return new PaymentOrder(
                        executorId,
                        orderId,
                        title,
                        description,
                        start,
                        end,
                        maxAmount,
                        status,
                        map.get("RFCode"),
                        map.get("sourceIBAN"),
                        attempts,
                        executionFee
                );

            default:
                throw new IllegalArgumentException("Unknown standing order type: " + type);
        }
    }


}
