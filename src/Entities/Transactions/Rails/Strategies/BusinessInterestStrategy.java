package Entities.Transactions.Rails.Strategies;

public class BusinessInterestStrategy implements InterestStrategy {
    private static final float BusinessRate = 0.8f; // Example: 20% reduction for businesses
    @Override
    public double computeInterest(float interestRate) {
        
        return interestRate *  BusinessRate; // Π.χ., 20% μείωση για επιχειρήσεις
    }

}
