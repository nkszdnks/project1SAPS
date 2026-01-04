package Entities.Transactions.InterBankTransfers;


import Entities.Accounts.Statements.Statement;

public class ExecutionResult {

    private final boolean success;
    private final String message;
    private final String transactionId;
    private Statement statement;

    public ExecutionResult(boolean success, String message, String transactionId) {
        this.success = success;
        this.message = message;
        this.transactionId = transactionId;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public boolean isSuccess() {
        return success;
    }

    public Statement getStatement() {
        return statement;
    }

    public String getMessage() {
        return message;
    }

    public String getTransactionId() {
        return transactionId;
    }
}

