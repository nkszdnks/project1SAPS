package Entities.Transactions.InterBankTransfers;

import Entities.Transactions.Transfer;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class ExecutionContext {

    private Transfer transfer;


    public ExecutionContext(Transfer transfer) {
        this.transfer = transfer;
    }

    public SepaTransferRequest toSepaRequest() {
        HashMap<String, String> sepaDetails = transfer.getTransferDetails();
        SepaTransferRequest req = new SepaTransferRequest(
        transfer.getAmount(),
        sepaDetails.get("creditorName"), // or fetch from target account owner
        transfer.getTargetIBAN(),
        sepaDetails.get("creditorBic"),// placeholder
        sepaDetails.get("creditorBankName"),
        transfer.getTimestamp().toLocalDate().format(DateTimeFormatter.ISO_DATE),
        sepaDetails.get("charges"),
                transfer.getSourceIBAN());
        return req;
    }

    public SwiftTransferRequest toSwiftRequest() {
        HashMap<String, String> swiftDetails = transfer.getTransferDetails();
        SwiftTransferRequest req = new SwiftTransferRequest(
        swiftDetails.get("currency"),
        transfer.getAmount(),
        swiftDetails.get("beneficiaryName"),
                swiftDetails.get("beneficiaryAddress"),// target owner
        transfer.getTargetIBAN(),
                swiftDetails.get("bankName"),
                swiftDetails.get("swiftCode"),
        swiftDetails.get("country"),
                swiftDetails.get("charges"),
                false,
                transfer.getSourceIBAN()
        );
        return req;
    }

    public HashMap<String,String> toLocal(){
        HashMap<String,String> map = new HashMap<>();
        map.put("sourceIBAN", transfer.getSourceIBAN());
        map.put("targetIBAN", transfer.getTargetIBAN());
        map.put("bankFee",String.valueOf(transfer.getBankFee()));
        map.put("amount",String.valueOf(transfer.getAmount()));
        map.put("timestamp",String.valueOf(transfer.getTimestamp().toLocalDate().format(DateTimeFormatter.ISO_DATE)));
        map.put("transactionId",transfer.getTransactionId());
        return map;
    }
}

