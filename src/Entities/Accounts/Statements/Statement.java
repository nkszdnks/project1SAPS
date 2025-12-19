package Entities.Accounts.Statements;

import Entities.Accounts.BankAcount;
import Managers.AccountManager;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Statement {

    private String statementId;
    private LocalDateTime timestamp;
    private double amount;
    private double[] balanceAfter;
    private String description;
    private String[] ibansInvolved = new String[2];
    private String transactionId;// connection to Transaction

    public Statement(String statementId, LocalDateTime timestamp, double amount,
                     double[] balanceAfter, String description,String[] ibansInvolved, String transactionId) {
        this.statementId = "SID"+String.valueOf(timestamp.getMinute())+String.valueOf(timestamp.getSecond());
        this.timestamp = timestamp;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.ibansInvolved = ibansInvolved;
        this.transactionId = transactionId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getStatementId() { return statementId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getAmount() { return amount; }
    public double[] getBalanceAfter() { return balanceAfter; }
    public String getDescription() { return description; }
    public String[] getIbansInvolved() { return ibansInvolved; }
    public String getTransactionId() { return transactionId; }



    // ---------------------------------------------------------
    // CSV MARSHAL
    // ---------------------------------------------------------
    public String marshal() {
        return String.format(
                "statementId:%s,timestamp:%s,amount:%s,description:%s,iban1:%s,iban2:%s,balanceAfter1:%s,balanceAfter2:%s,transactionId:%s",
                statementId,
                timestamp,
                String.valueOf(amount),
                description,
                ibansInvolved[0] == null ? "" : ibansInvolved[0],
                ibansInvolved[1] == null ? "" : ibansInvolved[1],
                String.valueOf(balanceAfter[0]),
                balanceAfter[1] == 0 ? "" : String.format("%.2f", balanceAfter[1]),
                transactionId
        );
    }

    // ---------------------------------------------------------
    // CSV UNMARSHAL
    // ---------------------------------------------------------
    public static Statement unmarshal(String csvLine) {

        HashMap<String, String> map = new HashMap<>();

        // split on comma → key:value pairs
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
       double[]  balanceAfter = new double[2];
       balanceAfter[0] = Double.parseDouble(map.get("balanceAfter1"));
       balanceAfter[1] = Double.parseDouble(map.get("balanceAfter2").equals("") ? "0" : map.get("balanceAfter2"));

        return new Statement(
                map.get("statementId"),
                LocalDateTime.parse(map.get("timestamp")),
                Double.parseDouble(map.get("amount")),
                balanceAfter,
                map.get("description"),
                ibans,
                map.get("transactionId")
        );
    }



}

