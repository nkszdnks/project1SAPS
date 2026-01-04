package Entities.Transactions.InterBankTransfers;

public class SwiftTransferRequest {
    private String currency;
    private double amount;
    private final String sourceIBAN;


    private String beneficiaryName;
    private String beneficiaryAddress;
    private String beneficiaryAccount;

    private String bankName;
    private String swiftCode;
    private String country;

    public String getSourceIBAN() {
        return sourceIBAN;
    }

    private String charges;// SHA | OUR

    private Boolean correspondentBank;
    /* getters */
    public SwiftTransferRequest(
            String currency,
            double amount,
            String beneficiaryName,
            String beneficiaryAddress,
            String beneficiaryAccount,
            String bankName,
            String swiftCode,
            String country,
            String charges,
            Boolean correspondentBank,
            String sourceIBAN
    ) {
        this.currency = currency;
        this.amount = amount;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAddress = beneficiaryAddress;
        this.beneficiaryAccount = beneficiaryAccount;
        this.bankName = bankName;
        this.swiftCode = swiftCode;
        this.country = country;
        this.charges = charges;
        this.correspondentBank = correspondentBank;
        this.sourceIBAN = sourceIBAN;
    }



    public String getCurrency() { return currency; }
    public double getAmount() { return amount; }

    public String getBeneficiaryName() { return beneficiaryName; }
    public String getBeneficiaryAddress() { return beneficiaryAddress; }
    public String getBeneficiaryAccount() { return beneficiaryAccount; }

    public String getBankName() { return bankName; }
    public String getSwiftCode() { return swiftCode; }
    public String getCountry() { return country; }

    public String getCharges() { return charges; }

    public boolean getCorrespondentBank() {
        return correspondentBank;
    }
}
