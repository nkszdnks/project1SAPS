package Entities.Transactions.InterBankTransfers;

import java.time.LocalDateTime;

public class SepaTransferRequest {
    private final double amount;
    private final String sourceIBAN;
    private final String creditorName;
    private final String creditorIban;
    private final String bic;
    private final String bankName;
    private final String requestedDate;
    private final String charges; // SHA | OUR

    public SepaTransferRequest(double amount,
                               String creditorName,
                               String creditorIban,
                               String bic,
                               String bankName,
                               String requestedDate,
                               String charges,String sourceIBAN) {
        this.amount = amount;
        this.creditorName = creditorName;
        this.creditorIban = creditorIban;
        this.bic = bic;
        this.bankName = bankName;
        this.requestedDate = requestedDate;
        this.charges = charges;
        this.sourceIBAN = sourceIBAN;
    }

    public String getSourceIBAN() {
        return sourceIBAN;
    }

    // getters μόνο
    public double getAmount() { return amount; }
    public String getCreditorName() { return creditorName; }
    public String getCreditorIban() { return creditorIban; }
    public String getBic() { return bic; }
    public String getBankName() { return bankName; }
    public String getRequestedDate() { return requestedDate; }
    public String getCharges() { return charges; }
}
