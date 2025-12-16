package Entities.StandingOrders;

import java.time.LocalDate;
import java.util.HashMap;

public abstract class StandingOrder {

    protected int orderId;
    protected String title;
    protected String description;
    protected FrequencyType frequency;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected double maxAmount;
    protected OrderStatus status;
    private int attempts=0;
    private String chargeIBAN;

    public StandingOrder(int orderId, String title, String description,
                         FrequencyType frequency, LocalDate startDate,
                         LocalDate endDate, double maxAmount, OrderStatus status, String chargeIBAN) {

        this.orderId = orderId;
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxAmount = maxAmount;
        this.status = status;
        this.attempts = 0;
        this.chargeIBAN = chargeIBAN;
    }

    public int getOrderId() { return orderId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public FrequencyType getFrequency() { return frequency; }
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

    // Key:value CSV marshal
    public String marshalBase() {
        return "orderId:" + orderId +
                ",title:" + title +
                ",description:" + description +
                ",frequency:" + frequency +
                ",startDate:" + startDate +
                ",endDate:" + endDate +
                ",maxAmount:" + maxAmount +
                ",status:" + status;
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
        int orderId = Integer.parseInt(map.get("orderId"));
        String title = map.get("title");
        String description = map.get("description");
        FrequencyType frequency = FrequencyType.valueOf(map.get("frequency"));
        LocalDate start = LocalDate.parse(map.get("startDate"));
        LocalDate end = LocalDate.parse(map.get("endDate"));
        double maxAmount = Double.parseDouble(map.get("maxAmount"));
        OrderStatus status = OrderStatus.valueOf(map.get("status"));

        switch (type) {

            case "TRANSFER_ORDER":
                return new TransferOrder(
                        orderId,
                        title,
                        description,
                        frequency,
                        start,
                        end,
                        maxAmount,
                        status,
                        map.get("targetIBAN"),
                        Double.parseDouble(map.get("amount")),
                        map.get("sourceIBAN")
                );

            case "PAYMENT_ORDER":
                return new PaymentOrder(
                        orderId,
                        title,
                        description,
                        frequency,
                        start,
                        end,
                        maxAmount,
                        status,
                        map.get("RFCode"),
                        Double.parseDouble(map.get("amount")),
                        map.get("sourceIBAN"),
                        LocalDate.parse(map.get("expirationDate"))
                );

            default:
                throw new IllegalArgumentException("Unknown standing order type: " + type);
        }
    }


}
