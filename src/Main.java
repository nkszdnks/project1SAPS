import DataAccessObjects.AcountsDAO;
import DataAccessObjects.FactoryDAO;
import DataAccessObjects.UsersDAO;
import Entities.Accounts.BankAcount;
import Entities.Accounts.BusinessAcount;
import Entities.Accounts.PersonalAccount;
import Entities.Transactions.InterBankTransfers.*;
import Entities.Transactions.TransactionStatus;
import Entities.Transactions.Transfer;
import Entities.Users.User;
import Entities.Users.IndividualPerson;
import Entities.Users.Business;
import Entities.Users.Admin;
import Managers.BillManager;
import Managers.UserManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {


        BankTransferApiClient bankTransferApiClient = new BankTransferApiClient();
        Transfer transfer = new Transfer( LocalDateTime.now(),100,"Test","4058954", TransactionStatus.PENDING,"GR100202503111546001","DU552202603111549009",2.4,"Transfer");
        ExecutionContext executionContext = new ExecutionContext(transfer);
        SepaTransferRequest  sepaTransferRequest = executionContext.toSepaRequest();
        SepaExecutor sepaExecutor = new SepaExecutor(bankTransferApiClient);
        SwiftTransferRequest swiftTransferRequest = executionContext.toSwiftRequest();
        SwiftExecutor swiftExecutor = new SwiftExecutor(bankTransferApiClient);

        for(int i = 0;i<100;i++) {
            ExecutionResult executionResult = swiftExecutor.execute(executionContext);
            System.out.println(executionResult.isSuccess() + executionResult.getMessage() + executionResult.getTransactionId());
        }
        }
}
