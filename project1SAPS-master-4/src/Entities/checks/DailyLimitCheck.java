package Entities.checks;
import Entities.Transactions.Requests.TransactionRequest;


public class DailyLimitCheck extends BaseCheck {
    private final double DayLimit =  5000.0;
    @Override
    protected boolean apply(TransactionRequest req) {
        return req.getAmount() <= DayLimit;
    }
    @Override
    protected String failMessage() { return "Daily limit exceeded"; }
}