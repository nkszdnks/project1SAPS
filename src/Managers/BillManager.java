package Managers;

import DataAccessObjects.BillsDAO;
import DataAccessObjects.FactoryDAO;
import Entities.Users.BillStatus;
import Entities.Users.Bills;
import Entities.Users.Business;
import Entities.Users.Customer;
import swinglab.View.AppMediator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillManager implements Manager {

    private static BillManager INSTANCE;

    private final BillsDAO billsDAO;

    private ArrayList<Bills> issued = new ArrayList<>();
    private ArrayList<Bills> paid = new ArrayList<>();
    private ArrayList<Bills> expired = new ArrayList<>();
    private ArrayList<Bills> futureBills = new ArrayList<>();

    private boolean loaded = false;

    private BillManager() {
        FactoryDAO factoryDAO = FactoryDAO.getInstance();
        billsDAO = factoryDAO.getBillsDAO();
    }

    public static BillManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BillManager();
        }
        return INSTANCE;
    }

    // --------------------------------------------------
    // INTERNAL LOAD (CRITICAL FIX)
    // --------------------------------------------------
    private void loadIfNeeded() {
        if (!loaded) {
            restore();
            loaded = true;
        }
    }

    // --------------------------------------------------
    // CRUD
    // --------------------------------------------------
    public void create(
            String RF,
            String billNumber,
            int amount,
            Business issuer,
            Customer customer,
            LocalDate issueDate,
            LocalDate dueDate) {

        loadIfNeeded();

        Bills bill = new Bills(
                RF,
                billNumber,
                amount,
                issuer,
                customer,
                issueDate,
                dueDate,
                BillStatus.PENDING
        );

        issued.add(bill);
    }

    public void billsPayed(Bills bill) {
        loadIfNeeded();

        bill.setStatus(BillStatus.PAID);
        issued.remove(bill);
        paid.add(bill);
    }

    public void deleteBill(String RF) {
        loadIfNeeded();

        Bills bill = findBill(RF);
        if (bill != null) {
            issued.remove(bill);
            paid.remove(bill);
            expired.remove(bill);
            futureBills.remove(bill);
        }
    }

    public Bills findBill(String RF) {
        loadIfNeeded();

        for (Bills bill : issued) {
            if (bill.getRF().equals(RF))
                return bill;
        }
        return null;
    }

    // --------------------------------------------------
    // FINDERS (FIXED)
    // --------------------------------------------------
    public ArrayList<Bills> findMyBills(String customerId) {
        loadIfNeeded();

        ArrayList<Bills> myBills = new ArrayList<>();
        for (Bills bill : issued) {
            if (bill.getCustomer() != null &&
                    bill.getCustomer().getUserId().equals(customerId)) {
                myBills.add(bill);
            }
        }
        return myBills;
    }

    public ArrayList<Bills> findCompanyIssuedBills(String issuerId) {
        loadIfNeeded();

        ArrayList<Bills> companyBills = new ArrayList<>();
        for (Bills bill : issued) {
            if (bill.getIssuer() != null &&
                    bill.getIssuer().getUserId().equals(issuerId)) {
                companyBills.add(bill);
            }
        }
        return companyBills;
    }

    public ArrayList<Bills> findCompanyPayedBills(String issuerId) {
        loadIfNeeded();

        ArrayList<Bills> companyBills = new ArrayList<>();
        for (Bills bill : paid) {
            if (bill.getIssuer() != null &&
                    bill.getIssuer().getUserId().equals(issuerId)) {
                companyBills.add(bill);
            }
        }
        return companyBills;
    }

    // --------------------------------------------------
    // DAILY RESTORE
    // --------------------------------------------------
    public void restoreEachDay(LocalDate today) {

        loadIfNeeded();

        List<Bills> toMoveFromFuture = new ArrayList<>();
        List<Bills> toMoveFromIssued = new ArrayList<>();

        for (Bills bill : futureBills) {
            if (!today.isBefore(bill.getIssueDate())) {
                issued.add(bill);
                toMoveFromFuture.add(bill);
            }
        }

        for (Bills bill : issued) {
            if (today.isAfter(bill.getDueDate())) {
                bill.setStatus(BillStatus.EXPIRED);
                expired.add(bill);
                toMoveFromIssued.add(bill);
            }
        }

        futureBills.removeAll(toMoveFromFuture);
        issued.removeAll(toMoveFromIssued);
    }

    // --------------------------------------------------
    // PERSISTENCE
    // --------------------------------------------------
    @Override
    public void restore() {
        billsDAO.loadBills(AppMediator.getToday());
        issued = new ArrayList<>(billsDAO.getIssued());
        paid = new ArrayList<>(billsDAO.getPaid());
        expired = new ArrayList<>(billsDAO.getExpired());
        futureBills = new ArrayList<>(billsDAO.getFutureBills());
    }

    @Override
    public void save() {
        List<Bills> all = getAllBills();
        billsDAO.saveBills(all);
    }

    // --------------------------------------------------
    // GETTERS
    // --------------------------------------------------
    public ArrayList<Bills> getAllBills() {
        ArrayList<Bills> all = new ArrayList<>();
        all.addAll(issued);
        all.addAll(paid);
        all.addAll(expired);
        all.addAll(futureBills);
        return all;
    }
}
