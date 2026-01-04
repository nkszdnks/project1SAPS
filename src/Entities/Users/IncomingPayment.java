package Entities.Users;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Εισερχόμενη πίστωση προς επιχείρηση
 */
public class IncomingPayment {

    private final String id;               // μοναδικός κωδικός
    private final String businessVAT;      // VAT της επιχείρησης που εισπράττει
    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime timestamp;

    public IncomingPayment(String id, String businessVAT,
                           BigDecimal amount, String description,
                           LocalDateTime timestamp) {
        this.id = id;
        this.businessVAT = businessVAT;
        this.amount = amount;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getId()            { return id; }
    public String getBusinessVAT()   { return businessVAT; }
    public BigDecimal getAmount()    { return amount; }
    public String getDescription()   { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }

    /* ---------- CSV ---------- */
    public String marshal() {
        return String.join(",",
                "id:" + id,
                "businessVAT:" + businessVAT,
                "amount:" + amount,
                "description:" + description,
                "timestamp:" + timestamp);
    }

    public static IncomingPayment unmarshal(String csvLine) {
        String[] parts = csvLine.split(",", -1);
        String id          = parts[0].split(":", 2)[1];
        String businessVAT = parts[1].split(":", 2)[1];
        BigDecimal amount  = new BigDecimal(parts[2].split(":", 2)[1]);
        String description = parts[3].split(":", 2)[1];
        LocalDateTime ts   = LocalDateTime.parse(parts[4].split(":", 2)[1]);
        return new IncomingPayment(id, businessVAT, amount, description, ts);
    }

    @Override
    public String toString() {
        return "IncomingPayment[" + id + ", " + amount + ", " + timestamp + "]";
    }
}
