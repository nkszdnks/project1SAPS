package Entities.Transactions.Rails;

import Entities.AdminRequests.DepositAdminRequest;
import Managers.AdminRequestsManager;

public class DepositRail {


        public DepositRail() {
        }

        public String execute(DepositAdminRequest adminRequest){
            AdminRequestsManager.getInstance().getAdminRequests().add(adminRequest);
            return "Request send successfully! Waiting for admin's approval.";
        }
    }

