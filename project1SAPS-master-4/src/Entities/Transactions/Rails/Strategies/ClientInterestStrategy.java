package Entities.Transactions.Rails.Strategies;

public class ClientInterestStrategy implements InterestStrategy {
    @Override
    public double computeInterest(float interestRate) {
        // Πελάτης: Κανονικός επιτοκιακός τύπος
        return interestRate;
    }

}
