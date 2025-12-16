package Managers;



import DataAccessObjects.FactoryDAO;
import DataAccessObjects.StandingOrdersDAO;
import Entities.StandingOrders.StandingOrder;
import Entities.StandingOrders.TransferOrder;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public class StandingOrderManager implements Manager {
    private static  StandingOrderManager INSTANCE;
    private FactoryDAO factoryDAO;
    private StandingOrdersDAO StandingOrdersDAO;
    private ArrayList<StandingOrder> active = new ArrayList<>();
    private ArrayList<StandingOrder> expired = new ArrayList<>();
    private ArrayList<StandingOrder> failed = new ArrayList<>();
    
    private StandingOrderManager() {
        factoryDAO = FactoryDAO.getInstance();
        StandingOrdersDAO = factoryDAO.getStandingOrdersDAO();
    }

    public static StandingOrderManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StandingOrderManager();
            return INSTANCE;
        }
        return INSTANCE;
    }

    public ArrayList<StandingOrder> getActive() {
        return active;
    }
    public ArrayList<StandingOrder> getExpired() {
        return expired;
    }
    public ArrayList<StandingOrder> getFailed() {
        return failed;
    }

    public void createOrder(StandingOrder standingOrder) {
        active.add(standingOrder);
    }

    @Override
    public void restore(){
        active = StandingOrdersDAO.loadActiveStandingOrders();

        expired = StandingOrdersDAO.loadExpiredStandingOrders();
        failed = StandingOrdersDAO.loadFailedStandingOrders();
    }

    @Override
    public void save() {
        StandingOrdersDAO.saveActiveStandingOrders(active);
        StandingOrdersDAO.saveExpriredStandingOrders(expired);
        StandingOrdersDAO.saveFailedStandingOrders(failed);
    }
//    @Override
//    public void save(){
//        Storager.getInstance().load(active,"./data/orders/active.csv");
//        Storager.getInstance().load(expired,"./data/orders/expired.csv");
//        Storager.getInstance().load(failed,"./data/orders/failed.csv");
//        executeOrder();
//    }

    public void deleteOrder(StandingOrder standingOrder) {
        active.remove(standingOrder);
        expired.remove(standingOrder);
        failed.remove(standingOrder);
    }

//    public void executeOrder() {
//        ArrayList<StandingOrder> toRemove = new ArrayList<>();
//        TransferOrder transferOrder;
//        PaymentOrder paymentOrder;
//        boolean isSuccesful;
//        for (StandingOrder standingOrder : active.getList()) {
//            if(standingOrder.getEndDate().equals(MainMenu.today)){
//                toRemove.add(standingOrder);
//                expired.getList().add(standingOrder);
//                continue;
//            }
//            if(standingOrder.getType().equals("PaymentOrder")){
//                paymentOrder = (PaymentOrder) standingOrder;
//                if (BillManager.getInstance().findBill(paymentOrder.getRF()) == null)
//                    continue;
//                if(paymentOrder.getExpirationDate().equals(MainMenu.today)||paymentOrder.getExpirationDate().isBefore(MainMenu.today)){
//                    do {
//                        isSuccesful = paymentOrder.attemptOrder();
//                    } while (paymentOrder.getAttempts() < 3 && !isSuccesful);
//                    if(paymentOrder.getAttempts()>=3&&!isSuccesful){
//                        failed.getList().add(standingOrder);
//                    }
//                    toRemove.add(standingOrder);
//                }
//            }
//            else if(standingOrder.getType().equals("TransferOrder")){
//                transferOrder = (TransferOrder) standingOrder;
//                long monthsPast = transferOrder.getStartDate().until(MainMenu.today).toTotalMonths();
//                if(transferOrder.getDayOfMonth()==MainMenu.today.getDayOfMonth() && monthsPast>0 && monthsPast%transferOrder.getTransferFrequency()==0){
//                    do {
//                        isSuccesful = transferOrder.attemptOrder();
//                    }while (transferOrder.getAttempts() < 3 && !isSuccesful);
//                    if(transferOrder.getAttempts()>=3&&!isSuccesful) {
//                        failed.getList().add(standingOrder);
//                        toRemove.add(standingOrder);  // newLine
//                    }
//                }
//            }
//        }
//        for(StandingOrder standingOrder : toRemove){
//            active.getList().remove(standingOrder);
//        }
//    }
}