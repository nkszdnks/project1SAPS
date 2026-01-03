package Managers;



import Commands.ScheduledTransferCommand;
import DataAccessObjects.FactoryDAO;
import DataAccessObjects.StandingOrdersDAO;
import Entities.Accounts.BankAcount;
import Entities.Accounts.Statements.FailedOrderStatement;
import Entities.StandingOrders.OrderStatus;
import Entities.StandingOrders.StandingOrder;
import Entities.Transactions.Requests.TransferRequest;
import Entities.Transactions.TransactionStatus;
import Entities.Users.Customer;
import swinglab.View.AppMediator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

public class StandingOrderManager implements Manager {
    private static StandingOrderManager INSTANCE;
    private FactoryDAO factoryDAO;
    private StandingOrdersDAO StandingOrdersDAO;
    private ArrayList<StandingOrder> active = new ArrayList<>();
    private ArrayList<StandingOrder> expired = new ArrayList<>();
    private ArrayList<FailedOrderStatement> failed = new ArrayList<>();
    private ArrayList<ScheduledTransferCommand> scheduledTransfers = new ArrayList<>();

    public ArrayList<ScheduledTransferCommand> getScheduledTransfers() {
        return scheduledTransfers;
    }

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
//    public void pauseStandingOrder(StandingOrder standingOrder) {
//        paused.add(standingOrder);
//        active.remove(standingOrder);
//    }

    public ArrayList<StandingOrder> getMyActive(Customer customer) {
        ArrayList<StandingOrder> myActive = new ArrayList<>();
        for (StandingOrder s : active) {
            if (s.getExecutorID().equals(customer.getUserId())) {
                myActive.add(s);
            }
        }
        return myActive;
    }

    public ArrayList<StandingOrder> getMyExpired(Customer customer) {
        ArrayList<StandingOrder> myExpired = new ArrayList<>();
        for (StandingOrder s : expired) {
            if (s.getExecutorID().equals(customer.getUserId())) {
                myExpired.add(s);
            }
        }
        return myExpired;
    }


    public ArrayList<FailedOrderStatement> getMyFailed(Customer customer) {
        ArrayList<FailedOrderStatement> myFailed = new ArrayList<>();
        for (FailedOrderStatement f : failed) {
            if (f.getExecutorID().equals(customer.getUserId())) {
                myFailed.add(f);
            }
        }
        return myFailed;
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
    public void executeScheduledTransfers(){
        ArrayList<ScheduledTransferCommand> toRemove = new ArrayList<>();
        for (ScheduledTransferCommand scheduledTransferCommand : scheduledTransfers) {
            if(scheduledTransferCommand.getScheduledDate().equals(AppMediator.getToday())){
                scheduledTransferCommand.execute();
                if(scheduledTransferCommand.getStatus().equals(TransactionStatus.COMPLETED)){
                    toRemove.add(scheduledTransferCommand);
                }
            }
        }
        for(ScheduledTransferCommand s:toRemove){
            scheduledTransfers.remove(s);
        }
    }

    public void executeOrders() {
        ArrayList<StandingOrder> toRemove = new ArrayList<>();

        boolean isSuccesful;
        for (StandingOrder standingOrder : active) {
            if(standingOrder.getEndDate().equals(AppMediator.getToday())){
                toRemove.add(standingOrder);
                standingOrder.setStatus(OrderStatus.EXPIRED);
                expired.add(standingOrder);
                continue;
            }
            if(standingOrder.getStatus().equals(OrderStatus.PAUSED)){
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
    }

    private FailedOrderStatement creteFailedStatement(StandingOrder standingOrder) {
        BankAcount chargeAccount = AccountManager.getInstance().findAccountByIBAN(standingOrder.getChargeIBAN());
        double []balances = {chargeAccount.getAccountBalance(),0.0};
        String []ibansInvolved = {standingOrder.getChargeIBAN(),""};
        return new FailedOrderStatement(AppMediator.getToday().atTime(LocalTime.now()),standingOrder.getAmount(),balances, standingOrder.getDescription(),standingOrder.getTitle(),ibansInvolved,standingOrder.getOrderId(),standingOrder.getExecutorID(),standingOrder.getType(),standingOrder.getFailureReason(),standingOrder.getAttempts() ,standingOrder.getExecutionFee());
    }

    public StandingOrder findOrderByID(String orderID) {
        for(StandingOrder s : active){
            if(s.getOrderId().equals(orderID)){
                return s;
            }
        }
        for(StandingOrder s : expired){
            if(s.getOrderId().equals(orderID)){
                return s;
            }
        }
        return null;
    }

    public void deleteOrder(StandingOrder order){

        active.remove(order);
        expired.remove(order);
    }

    public boolean deleteScheduledTransferByRequest(TransferRequest request) {
        if (request == null) return false;

        Iterator<ScheduledTransferCommand> iterator = scheduledTransfers.iterator();

        while (iterator.hasNext()) {
            ScheduledTransferCommand cmd = iterator.next();

            if (request.equals(cmd.getreq())) {
                iterator.remove();   // SAFE removal during iteration
                return true;         // deleted successfully
            }
        }
        return false; // not found
    }
}