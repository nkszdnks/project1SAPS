package Entities.Accounts.Statements;

import java.time.LocalDateTime;
import java.util.HashMap;

public class FailedOrderStatement extends Statement {

    private String orderId;
    private String orderType;
    private String failureReason;
    private String title;
    private int attempts;

    public FailedOrderStatement(
            String statementId,
            LocalDateTime timestamp,
            double amount,
            double[] balanceAfter,
            String description,
            String title,
            String[] ibansInvolved,
            String orderId,
            String orderType,
            String failureReason,
            int attempts
    ) {
        super(statementId, timestamp, amount, balanceAfter, description, ibansInvolved, orderId);
        this.orderId = orderId;
        this.orderType = orderType;
        this.failureReason = failureReason;
        this.attempts = attempts;
        this.title = title;
    }

    /* ================= GETTERS ================= */

    public String getOrderId() {
        return orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public String getTitle() {
        return title;
    }

    public int getAttempts() {
        return attempts;
    }

    /* ================= CSV MARSHAL ================= */

    @Override
    public String marshal() {
        return super.marshal() +
                ",title:" + title +
                ",orderType:" + orderType +
                ",failureReason:" + failureReason +
                ",attempts:" + attempts +
                ",statementType:FAILED_ORDER";
    }

    /* ================= CSV UNMARSHAL ================= */

    public static FailedOrderStatement unmarshal(String csvLine) {

        HashMap<String, String> map = new HashMap<>();

        String[] parts = csvLine.split(",");
        for (String p : parts) {
            String[] kv = p.split(":", 2);
            if (kv.length == 2) {
                map.put(kv[0].trim(), kv[1].trim());
            }
        }

        String[] ibans = new String[2];
        ibans[0] = map.get("iban1");
        ibans[1] = map.get("iban2");

        double[] balanceAfter = new double[2];
        balanceAfter[0] = Double.parseDouble(map.get("balanceAfter1"));
        balanceAfter[1] = map.get("balanceAfter2") == null || map.get("balanceAfter2").isEmpty()
                ? 0
                : Double.parseDouble(map.get("balanceAfter2"));

        return new FailedOrderStatement(
                map.get("statementId"),
                LocalDateTime.parse(map.get("timestamp")),
                Double.parseDouble(map.get("amount")),
                balanceAfter,
                map.get("description"),
                map.get("title"),
                ibans,
                map.get("transactionId"),
                map.get("orderType"),
                map.get("failureReason") == null ?"No Reason":map.get("failureReason"),
                Integer.parseInt(map.get("attempts"))
        );
    }
}

