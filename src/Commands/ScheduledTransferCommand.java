package Commands;

import Entities.Transactions.Rails.TransferRail;
import Entities.Transactions.Requests.TransferRequest;
import Entities.Transactions.TransactionStatus;

import java.time.LocalDate;
import java.util.HashMap;

public class ScheduledTransferCommand implements BankCommand{
    private TransferRequest req;
    private LocalDate scheduledDate;
    private TransactionStatus status;
    private String Message;
    private HashMap<String, String> TransferDetails;
    public ScheduledTransferCommand(TransferRequest req, LocalDate scheduledDate,HashMap<String, String> TransferDetails) {
        this.req = req;
        this.scheduledDate = scheduledDate;
        this.TransferDetails = TransferDetails;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return Message;
    }

    @Override
    public void execute() {
        TransferRail transferRail = new TransferRail();
        if(transferRail.execute(req,TransferDetails)){
            setStatus(TransactionStatus.COMPLETED);
        }
        else {
            setStatus(TransactionStatus.FAILED);
        }
        setMessage(transferRail.getMessage());
    }

    private void setMessage(String message) {
        this.Message = message;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    @Override
    public void undo() {

    }
}
