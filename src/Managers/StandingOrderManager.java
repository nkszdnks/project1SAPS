package Managers;



import DataAccessObjects.FactoryDAO;
import DataAccessObjects.StandingOrdersDAO;
import Entities.Accounts.BankAcount;
import Entities.Accounts.Statements.FailedOrderStatement;
import Entities.StandingOrders.OrderStatus;
import Entities.StandingOrders.PaymentOrder;
import Entities.StandingOrders.StandingOrder;
import Entities.StandingOrders.TransferOrder;
import swinglab.AppMediator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public class StandingOrderManager implements Manager {
    private static StandingOrderManager INSTANCE;
    private FactoryDAO factoryDAO;
    private StandingOrdersDAO StandingOrdersDAO;
    private ArrayList<StandingOrder> active = new ArrayList<>();
    private ArrayList<StandingOrder> expired = new ArrayList<>();
    private ArrayList<FailedOrderStatement> failed = new ArrayList<>();

    private StandingOrderManager() {
        factoryDAO = FactoryDAO.getInstance();
        StandingOrdersDAO = factoryDAO.getStandingOrdersDAO();
    }

    public static StandingOrderManager getInstance() {
        if (INSTANCE == null) {
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

    public ArrayList<FailedOrderStatement> getFailed() {
        return failed;
    }

    public void createOrder(StandingOrder standingOrder) {
        active.add(standingOrder);
    }

    @Override
    public void restore() {
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

//    public void deleteOrder(StandingOrder standingOrder) {
//        active.remove(standingOrder);
//        expired.remove(standingOrder);
//        failed.remove(standingOrder);
//    }

    public void executeOrders() {
        ArrayList<StandingOrder> toRemove = new ArrayList<>();

        boolean isSuccesful;
        for (StandingOrder standingOrder : active) {
            if(standingOrder.getEndDate().equals(AppMediator.getToday())){
                toRemove.add(standingOrder);
                expired.add(standingOrder);
                continue;
            }
            standingOrder.computeNextExecutionDate(AppMediator.getToday());
            if (standingOrder.getExecutionDate().equals(AppMediator.getToday())&&standingOrder.getStatus().equals(OrderStatus.ACTIVE)) {
                //toRemove.add(standingOrder);
                //expired.getList().add(standingOrder);

                isSuccesful = standingOrder.executeOrder();
                if (!isSuccesful && standingOrder.getAttempts() == 3) {
                    //toRemove.add(standingOrder);
                    failed.add(creteFailedStatement(standingOrder));
                    standingOrder.setAttempts(0);

                }

            }
        }
        for(StandingOrder standingOrder : toRemove){
           active.remove(standingOrder);
            }
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

    private FailedOrderStatement creteFailedStatement(StandingOrder standingOrder) {
        BankAcount chargeAccount = AccountManager.getInstance().findAccountByIBAN(standingOrder.getChargeIBAN());
        double []balances = {chargeAccount.getAccountBalance(),0.0};
        String []ibansInvolved = {standingOrder.getChargeIBAN(),""};
        return new FailedOrderStatement("gayMan",AppMediator.getToday().atTime(LocalTime.now()),standingOrder.getAmount(),balances, standingOrder.getDescription(),standingOrder.getTitle(),ibansInvolved,standingOrder.getOrderId(),standingOrder.getType(),standingOrder.getFailureReason(),standingOrder.getAttempts() );
    }
}