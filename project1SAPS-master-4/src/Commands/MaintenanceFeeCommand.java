package Commands;

import Entities.Accounts.BusinessAcount;
import Entities.Transactions.InterBankTransfers.LocalExecutor;
import Entities.Transactions.TransactionStatus;
import Entities.Transactions.Transfer;
import Managers.TransactionManager;
import swinglab.View.AppMediator;

import java.time.LocalTime;

public class MaintenanceFeeCommand implements BankCommand {

    private BusinessAcount account;

    public MaintenanceFeeCommand(BusinessAcount account) {
        this.account = account;
    }

    @Override
    public void execute() {
        double amount = account.getMaintenanceFee();
        Transfer transfer = new Transfer(AppMediator.getToday().atTime(LocalTime.now()),amount,"Maintenance fee","BankOfTucID", TransactionStatus.PENDING,account.getIBAN(),AppMediator.getBankOfTucAccount().getIBAN(), 0.0,"");
        transfer.setTransferExecutor(new LocalExecutor());
        TransactionManager.getInstance().Transact(transfer);
    }

    @Override
    public void undo() {

    }
}
