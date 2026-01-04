package Entities.Transactions.Rails.Strategies;


import Entities.Transactions.Requests.TransferRequest;
public class SwiftFeeStrategy implements FeeStrategy {
    @Override
    public double computeFee(TransferRequest req) {
        // 0.2% του ποσού, min 5€, max 50€
        double fee = req.getAmount() * 0.002;
        if (fee < 5.0) fee = 5.0;
        if (fee > 50.0) fee = 50.0;
        return fee;
    }
}
