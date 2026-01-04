package Managers;

import Entities.Users.BusinessBill;
import Entities.Users.IncomingPayment;
import Entities.Users.Business;
import Entities.Users.BillStatus;
import DataAccessObjects.BusinessBillDAO;
import DataAccessObjects.IncomingPaymentDAO;
import java.util.ArrayList;
import java.util.List;

public class BusinessBillManager {
    private static BusinessBillManager instance;
    private final BusinessBillDAO billDAO;
    private final IncomingPaymentDAO paymentDAO;
    private List<BusinessBill> billsCache;
    private List<IncomingPayment> paymentsCache;

    private BusinessBillManager() {
        billDAO     = new BusinessBillDAO("data/business_bills.csv");
        paymentDAO  = new IncomingPaymentDAO("data/incoming_payments.csv");
    }

    public static BusinessBillManager getInstance() {
        if (instance == null) instance = new BusinessBillManager();
        return instance;
    }

    /* ---------- bills ---------- */
    public ArrayList<BusinessBill> getUnpaidBills(Business b) {
        ArrayList<BusinessBill> unpaid = new ArrayList<>();
        for (BusinessBill bill : billsCache) {
            if (bill.getBusinessVAT().equals(b.getVAT())
                    && bill.getBillStatus() == BillStatus.PENDING) {
                unpaid.add(bill);
            }
        }
        return unpaid;
    }

    /* ---------- payments ---------- */
    public ArrayList<IncomingPayment> getIncomingPayments(Business b) {
        ArrayList<IncomingPayment> list = new ArrayList<>();
        for (IncomingPayment p : paymentsCache) {
            if (p.getBusinessVAT().equals(b.getVAT())) list.add(p);
        }
        return list;
    }

    /* ---------- persist ---------- */
    public void save() {
        billDAO.saveAll(billsCache);
        paymentDAO.saveAll(paymentsCache);
    }

    public void restore() {
        billsCache     = new ArrayList<>(billDAO.loadAll());
        paymentsCache  = new ArrayList<>(paymentDAO.loadAll());
    }
}