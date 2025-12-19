package Entities.Transactions;

import Entities.Accounts.BankAcount;
import Entities.Accounts.BusinessAcount;
import Entities.Accounts.Statements.Statement;
import Entities.Users.BillStatus;
import Entities.Users.Bills;
import Managers.AccountManager;
import Managers.BillManager;
import Managers.StatementManager;

import java.time.LocalDateTime;

public class Payment extends Transaction {

    private String sourceIBAN;
    private String BillRFCode;


    public Payment(String transactionId, LocalDateTime timestamp, double amount,
                   String reason, String executorID, TransactionStatus status,
                   String sourceIBAN, String BIRFCode) {
        super(transactionId, timestamp, amount, reason, executorID, status);
        this.sourceIBAN = sourceIBAN;
        this.BillRFCode = BIRFCode;
    }

    public String getSourceIBAN() { return sourceIBAN; }
    public String getBIRFCode() { return BillRFCode; }


    @Override
    public String getType() { return "PAYMENT"; }

    @Override
    protected void createStatement(BankAcount source, BankAcount target) {
        String[] ibansInvolved = {sourceIBAN, target.getIBAN()};
        double[] remainingBalances = {source.getAccountBalance(), target.getAccountBalance()};
        Statement accountStatements = new Statement(super.getTransactionId(),getTimestamp(),getAmount(),  remainingBalances,getReason(),ibansInvolved,getTransactionId());
        source.addStatements(accountStatements);
        target.addStatements(accountStatements);
        StatementManager.getInstance().createStatement(accountStatements);
    }



    @Override
    public void Transact() {
        BankAcount source = AccountManager.getInstance().findAccountByIBAN(sourceIBAN);
        Bills bill = BillManager.getInstance().findBill(BillRFCode);
        BusinessAcount target = bill.getIssuer().getCorporateAcount();
        source.setAccountBalance(source.getAccountBalance() - bill.getAmount());
        target.setAccountBalance(target.getAccountBalance() + bill.getAmount());
        bill.setStatus(BillStatus.PAID);
        setStatus(TransactionStatus.COMPLETED);
        createStatement(source, target);
        BillManager.getInstance().billsPayed(bill);

    }
}
