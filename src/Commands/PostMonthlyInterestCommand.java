package Commands;

import Entities.Accounts.BankAcount;
import Entities.Transactions.InterBankTransfers.LocalExecutor;
import Entities.Transactions.TransactionStatus;
import Entities.Transactions.Transfer;
import Managers.TransactionManager;
import swinglab.AppMediator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PostMonthlyInterestCommand implements BankCommand {

    private BankAcount account;


    public PostMonthlyInterestCommand(BankAcount account) {
        this.account = account;

    }

    @Override
    public void execute() {
        double amount = account.getAccruedInterest();
        if (amount > 0) {
            Transfer transfer = new Transfer("IntrestID", AppMediator.getToday().atTime(LocalTime.now()),amount,"Interest from bank","BankOfTucID", TransactionStatus.PENDING,AppMediator.getBankOfTucAccount().getIBAN(), account.getIBAN(), 0.0,"");
            transfer.setTransferExecutor(new LocalExecutor());
            TransactionManager.getInstance().Transact(transfer);
            account.resetAccruedInterest();
        }
    }

    @Override
    public void undo() {
        
    }
}

