package Commands;

import Entities.Accounts.BankAcount;

public class AccrueDailyInterestCommand implements BankCommand {

    private BankAcount account;
    private double accruedToday;

    public AccrueDailyInterestCommand(BankAcount account) {
        this.account = account;
    }

    @Override
    public void execute() {
        accruedToday = account.calculateDailyInterest();
        account.accrueDailyInterest(accruedToday);
    }

    @Override
    public void undo() {
        account.accrueDailyInterest(-accruedToday);
    }
}

