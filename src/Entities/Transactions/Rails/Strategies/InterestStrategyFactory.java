package Entities.Transactions.Rails.Strategies;

public class InterestStrategyFactory {
    public static InterestStrategy getStrategyFor(String rail){
        switch (rail) {
            case "IndividualAccount": return new ClientInterestStrategy();
            case "BusinessAccount": return new BusinessInterestStrategy();
            
            default: throw new IllegalArgumentException("Unsupported rail: " + rail);
        }
    }

}
