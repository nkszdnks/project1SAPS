package Entities.Transactions.Rails;

import Entities.AdminRequests.DepositAdminRequest;
import Entities.Transactions.Builders.TransferBuilder;
import Entities.Transactions.Requests.TransactionRequest;
import Entities.Transactions.Requests.TransferRequest;
import Entities.Transactions.Transaction;
import Entities.Transactions.TransactionStatus;
import Entities.checks.BalanceCheck;
import Entities.checks.DailyLimitCheck;
import Entities.checks.IbanFormatCheck;
import Entities.checks.TransactionCheck;
import Managers.AdminRequestsManager;
import Managers.TransactionManager;
import swinglab.AppMediator;

import java.time.LocalDateTime;

public class DepositRail {


        public DepositRail() {
        }

        public String execute(DepositAdminRequest adminRequest){
            // 1) Chain of Responsibility: validations
            try {
                if (!adminRequest.getBankAccount().getCustomer().equals(adminRequest.getCustomer())) throw new IllegalStateException("You don't own this account ");
            } catch (IllegalStateException e) {
                return "Deposit failed. Reason:"+ e.getMessage();
            }

//        // 2) Strategy: fee computation
//        FeeStrategy feeStrategy = FeeStrategyFactory.getStrategyFor(req);
//        double fee = feeStrategy.computeFee(req);
            AdminRequestsManager.getInstance().getAdminRequests().add(adminRequest);
            return "Request send successfully! Waiting for admin's approval.";
        }
    }

