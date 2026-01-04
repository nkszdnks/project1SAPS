package Entities.Transactions.Rails.Strategies;



import Entities.Transactions.Requests.TransferRequest;


public class FeeStrategyFactory {
    public static FeeStrategy getStrategyFor(TransferRequest req){
        switch (req.getRail()) {
            case LOCAL: return new LocalFeeStrategy();
            case SEPA: return new SepaFeeStrategy();
            case SWIFT: return new SwiftFeeStrategy();
            default: throw new IllegalArgumentException("Unsupported rail: " + req.getRail());
        }
    }
}