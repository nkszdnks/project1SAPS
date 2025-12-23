package Entities.Transactions;

import Entities.Accounts.BankAcount;
import Entities.Accounts.Statements.Statement;
import Entities.Transactions.InterBankTransfers.ExecutionContext;
import Entities.Transactions.InterBankTransfers.ExecutionResult;
import Entities.Transactions.InterBankTransfers.TransferExecutor;
import Managers.AccountManager;
import Managers.StatementManager;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Transfer extends Transaction {

    private String sourceIBAN;
    private String targetIBAN;
    private String type;
    private double bankFee;
    private TransferExecutor transferExecutor;
    private ExecutionResult executionResult;
    private HashMap<String,String> transferDetails =  new HashMap<>();

    public void setDetails(HashMap<String, String> Details) {
        this.transferDetails = Details;
    }

    public HashMap<String, String> getTransferDetails() {
        return transferDetails;
    }

    public Transfer(String transactionId, LocalDateTime timestamp, double amount,
                    String reason, String executorID, TransactionStatus status,
                    String sourceIBAN, String targetIBAN, double bankFee, String type) {
        super(transactionId, timestamp, amount, reason, executorID, status);
        this.sourceIBAN = sourceIBAN;
        this.targetIBAN = targetIBAN;
        this.bankFee = bankFee;
        this.type = "TRANSFER";
    }

    public void setTransferExecutor(TransferExecutor transferExecutor) {
        this.transferExecutor = transferExecutor;
    }
    private void setExecutionResult(ExecutionResult executionResult) {
        this.executionResult = executionResult;
    }

    public String getSourceIBAN() { return sourceIBAN; }
    public String getTargetIBAN() { return targetIBAN; }
    public double getBankFee() { return bankFee; }

    @Override
    public String getType() { return type; }

    @Override
    protected void createStatement(BankAcount source, BankAcount target) {
        String[] ibansInvolved = {sourceIBAN, targetIBAN};
        double[] remainingBalances = {source.getAccountBalance(), target==null?0.0:target.getAccountBalance()};
        Statement accountStatements = new Statement(getTimestamp(),getAmount(),  remainingBalances,getReason(),ibansInvolved,getTransactionId(),bankFee);
        StatementManager.getInstance().createStatement(accountStatements);
        source.addStatements(accountStatements);
        if(target != null) {
            target.addStatements(accountStatements);
        }

    }

    public ExecutionResult getExecutionResult() {
        return executionResult;
    }

    @Override
    public void Transact() {

        ExecutionResult executionResult = transferExecutor.execute(new ExecutionContext(this));
        setExecutionResult(executionResult);
        if(executionResult.isSuccess()) {
            BankAcount source = AccountManager.getInstance().findAccountByIBAN(sourceIBAN);
            BankAcount target = AccountManager.getInstance().findAccountByIBAN(targetIBAN);
            createStatement(source,target);
            super.setStatus(TransactionStatus.COMPLETED);
            return;
        }

        super.setStatus(TransactionStatus.FAILED);
    }
}
