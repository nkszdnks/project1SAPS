package Entities.Transactions.Rails.Strategies;

import Entities.Transactions.Requests.TransferRequest;
public interface FeeStrategy {
    double computeFee(TransferRequest req);
}
