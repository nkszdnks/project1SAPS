package Managers;

import DataAccessObjects.AcountsDAO;
import DataAccessObjects.AdminRequestsDAO;
import DataAccessObjects.FactoryDAO;
import Entities.Accounts.BankAcount;
import Entities.AdminRequests.AdminRequest;
import com.sun.net.httpserver.Request;

import java.util.ArrayList;

public class AdminRequestsManager implements Manager{
    private ArrayList<AdminRequest> adminRequests = new ArrayList<AdminRequest>();
    private FactoryDAO factoryDAO ;
    private AdminRequestsDAO adminsRequestsDAO ;
    private static  AdminRequestsManager INSTANCE;

    private AdminRequestsManager() {
        factoryDAO = FactoryDAO.getInstance();
        adminsRequestsDAO = factoryDAO.getAdminRequestsDAO();
    }
    public static AdminRequestsManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new AdminRequestsManager();
            return INSTANCE;
        }
        return INSTANCE;
    }

    public ArrayList<AdminRequest> getAdminRequests() {
        return adminRequests;
    }


    @Override
    public void restore() {
        adminRequests = (ArrayList<AdminRequest>) adminsRequestsDAO.loadRequests();
    }

    @Override
    public void save() {
        adminsRequestsDAO.saveAccounts(adminRequests);

    }

    public AdminRequest findRequestByID(String requestID) {
        for (AdminRequest req : adminRequests) {
            if (req.getRequestID().equals(requestID)) {
                return req;
            }
        }
        return null;
    }
}
