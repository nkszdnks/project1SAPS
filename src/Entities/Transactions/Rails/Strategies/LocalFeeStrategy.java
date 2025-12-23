package Entities.Transactions.Rails.Strategies;

import Entities.Transactions.Requests.TransferRequest;

public class LocalFeeStrategy implements FeeStrategy {
    @Override
    public double computeFee(TransferRequest req) {
        // 0€ αν ποσό <= 1000, αλλιώς 1€
        return req.getAmount() <= 1000.0 ? 0.0 : 1.0;
    }
}
