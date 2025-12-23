package Entities.Transactions.InterBankTransfers;

public interface TransferExecutor {
    ExecutionResult execute(ExecutionContext ctx);
}

