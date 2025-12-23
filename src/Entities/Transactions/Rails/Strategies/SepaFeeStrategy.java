package Entities.Transactions.Rails.Strategies;


import Entities.Transactions.Requests.TransferRequest;
public class SepaFeeStrategy implements FeeStrategy {
    @Override
    public double computeFee(TransferRequest req) {
        // σταθερά 0.30€
        return 0.30;
    }
}
