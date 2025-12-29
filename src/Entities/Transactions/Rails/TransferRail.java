package Entities.Transactions.Rails;


import Entities.Transactions.InterBankTransfers.BankTransferApiClient;
import Entities.Transactions.InterBankTransfers.LocalExecutor;
import Entities.Transactions.InterBankTransfers.SepaExecutor;
import Entities.Transactions.InterBankTransfers.SwiftExecutor;
import Entities.Transactions.Rails.Strategies.FeeStrategy;
import Entities.Transactions.Rails.Strategies.FeeStrategyFactory;
import Entities.Transactions.TransactionStatus;
import Entities.Transactions.Builders.TransferBuilder;
import Entities.Transactions.Requests.TransferRequest;
import Entities.Transactions.Transfer;
import Entities.checks.BalanceCheck;
import Entities.checks.DailyLimitCheck;
import Entities.checks.RecipientIbanCheck;
import Entities.checks.TransactionCheck;

import Managers.TransactionManager;
import swinglab.View.AppMediator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class TransferRail {
    private TransactionCheck checks;
    private String Message = "";
    
    public TransferRail() {
        var RecipientIbanCheck = new RecipientIbanCheck();
        var balance = new BalanceCheck();
        var daily = new DailyLimitCheck();
        RecipientIbanCheck.setNext(balance);
        balance.setNext(daily);
        
        checks = RecipientIbanCheck;
    }

    public Boolean execute(TransferRequest req, HashMap<String, String> Details) {
        // 1) Chain of Responsibility: validations
        
        try {
        	checks.handle(req);
        } catch (IllegalStateException e) {
            Message = "Transfer failed. Reason:"+ e.getMessage();
           return false;
        }

        // 2) Strategy: fee computation
        FeeStrategy feeStrategy = FeeStrategyFactory.getStrategyFor(req);
        double fee = req.getFee()==0.0? feeStrategy.computeFee(req):req.getFee();
        Transfer transfer = (Transfer) new TransferBuilder()
                .setSourceIBAN(req.getFromIban())
                .setBankFee(fee)
                .setType("TRANSFER")
                .setTargetIBAN(req.getToIban())
                .setAmount(req.getAmount())
                .setReason(req.getReason())
                .setExecutorID(AppMediator.getUser().getUserId())
                .setStatus(TransactionStatus.PENDING)
                .setTimestamp(AppMediator.getToday().atTime(LocalTime.now()))
                .build();

        switch(req.getRail()){
            case LOCAL:
                LocalExecutor localExecutor = new LocalExecutor();
                transfer.setTransferExecutor(localExecutor);
                break;

            case SEPA:
                Details.put("requestedDate",String.valueOf(LocalDate.now()));
                transfer.setDetails(Details);
                BankTransferApiClient api = new BankTransferApiClient();
                SepaExecutor sepaExecutor = new SepaExecutor(api);
                transfer.setTransferExecutor(sepaExecutor);
                break;
            case SWIFT:
                Details.put("requestedDate",String.valueOf(LocalDate.now()));
                transfer.setDetails(Details);
                BankTransferApiClient api2 = new BankTransferApiClient();
                SwiftExecutor swiftExecutor = new SwiftExecutor(api2);
                transfer.setTransferExecutor(swiftExecutor);
                break;
                default:
                    Message = "Transfer failed. Reason:"+ req.getRail();
                    return false;

        }
        TransactionManager.getInstance().Transact(transfer);
        Message = transfer.getExecutionResult().getMessage();
        return transfer.getExecutionResult().isSuccess();
    }

    public String getMessage() {
        return Message;
    }
}