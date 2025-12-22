package Entities.Users;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Υποχρέωση / λογαριασμός προς πληρωμή από επιχείρηση
 */
public class BusinessBill {

    

    private final String billCode;   // π.χ. ΔΕΗ-987654
    private final String businessVAT;// ποιος πρέπει να πληρώσει
    private final BigDecimal amount;
    private final String supplier;   // π.χ. "ΔΕΗ", "COSMOTE"
    private final LocalDate dueDate;
    private BillStatus status = BillStatus.PENDING;

    public BusinessBill(String billCode, String businessVAT,
                        BigDecimal amount, String supplier,
                        LocalDate dueDate) {
        this.billCode   = billCode;
        this.businessVAT= businessVAT;
        this.amount     = amount;
        this.supplier   = supplier;
        this.dueDate    = dueDate;
    }

    public String getBillCode()    { return billCode; }
    public String getBusinessVAT() { return businessVAT; }
    public BigDecimal getAmount()  { return amount; }
    public String getSupplier()    { return supplier; }
    public LocalDate getDueDate()  { return dueDate; }
    public BillStatus getBillStatus()      { return status; }

    public void markPaid() { status = BillStatus.PAID; }
    public boolean isOverdue() {
        return status == BillStatus.PENDING && LocalDate.now().isAfter(dueDate);
    }

    /* ---------- CSV ---------- */
    public String marshal() {
        return String.join(",",
                "billCode:" + billCode,
                "businessVAT:" + businessVAT,
                "amount:" + amount,
                "supplier:" + supplier,
                "dueDate:" + dueDate,
                "BillStatus:" + status);
    }

    public static BusinessBill unmarshal(String csvLine) {
        String[] parts     = csvLine.split(",", -1);
        String billCode    = parts[0].split(":", 2)[1];
        String businessVAT = parts[1].split(":", 2)[1];
        BigDecimal amount  = new BigDecimal(parts[2].split(":", 2)[1]);
        String supplier    = parts[3].split(":", 2)[1];
        LocalDate dueDate  = LocalDate.parse(parts[4].split(":", 2)[1]);
        BillStatus status      = BillStatus.valueOf(parts[5].split(":", 2)[1]);
        BusinessBill bb    = new BusinessBill(billCode, businessVAT, amount, supplier, dueDate);
        if (status == BillStatus.PAID) bb.markPaid();   // επαναφορά κατάστασης
        return bb;
    }

    @Override
    public String toString() {
        return "BusinessBill[" + billCode + ", " + supplier + ", " + amount + ", " + status + "]";
    }
}