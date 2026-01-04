package Managers;
import Entities.Transactions.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private static  TransactionManager instance;
    private List<Transaction> transactions;

    private TransactionManager() {
        transactions = new ArrayList<Transaction>();
    }

    public void Transact(Transaction transaction){
        transaction.Transact();
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public static TransactionManager getInstance() {
        if(instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

}

