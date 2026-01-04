//package Entities.Transactions;
//
//import Entities.Accounts.BankAcount;
//import Entities.Accounts.Statements.Statement;
//import Entities.Transactions.InterBankTransfers.TransferExecutor;
//import Managers.AccountManager;
//import Managers.StatementManager;
//
//import java.time.LocalDateTime;
//
//public class Transfer extends Transaction {
//
//    private String sourceIBAN;
//    private String targetIBAN;
//    private String type;
//    private double bankFee;
//    private TransferExecutor transferExecutor;
//
//    public Transfer(String transactionId, LocalDateTime timestamp, double amount,
//                    String reason, String executorID, TransactionStatus status,
//                    String sourceIBAN, String targetIBAN, double bankFee,String type) {
//        super(transactionId, timestamp, amount, reason, executorID, status);
//        this.sourceIBAN = sourceIBAN;
//        this.targetIBAN = targetIBAN;
//        this.bankFee = bankFee;
//        this.type = type;
//    }
//
////    public void setTransferExecutor(TransferExecutor transferExecutor) {
////        this.transferExecutor = transferExecutor;
////    }
//
//    public String getSourceIBAN() { return sourceIBAN; }
//    public String getTargetIBAN() { return targetIBAN; }
//    public double getBankFee() { return bankFee; }
//
//    @Override
//    public String getType() { return "TRANSFER"; }
//
//    @Override
//    protected void createStatement(BankAcount source, BankAcount target) {
//        String[] ibansInvolved = {sourceIBAN, targetIBAN};
//        double[] remainingBalances = {source.getAccountBalance(), target.getAccountBalance()};
//        Statement accountStatements = new Statement(super.getTransactionId(),getTimestamp(),getAmount(),  remainingBalances,getReason(),ibansInvolved,getTransactionId(),bankFee);
//        source.addStatements(accountStatements);
//        target.addStatements(accountStatements);
//        StatementManager.getInstance().createStatement(accountStatements);
//
//    }
//
//    @Override
//    public void Transact() {
//        BankAcount source = AccountManager.getInstance().findAccountByIBAN(sourceIBAN);
//        BankAcount target = AccountManager.getInstance().findAccountByIBAN(targetIBAN);
//        if(source == null || target == null){
//            super.setStatus(TransactionStatus.FAILED);
//            return;
//        }
//        if (source.getAccountBalance() - super.getAmount() >= 0) {
//            source.setAccountBalance(source.getAccountBalance() -super.getAmount() - bankFee);
//            target.setAccountBalance(target.getAccountBalance() + super.getAmount());
//            createStatement(source,target);
//            super.setStatus(TransactionStatus.COMPLETED);
//            return;
//        }
//        super.setStatus(TransactionStatus.FAILED);
//    }
//}
