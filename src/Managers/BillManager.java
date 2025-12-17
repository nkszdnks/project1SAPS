package Managers;



import DataAccessObjects.BillsDAO;
import DataAccessObjects.FactoryDAO;
import Entities.Users.BillStatus;
import Entities.Users.Bills;
import Entities.Users.Business;
import Entities.Users.Customer;
import Managers.Manager;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillManager implements Manager {
    private static BillManager INSTANCE;
    FactoryDAO factoryDAO;
    BillsDAO billsDAO;
    private ArrayList<Bills> issued = new ArrayList<>();
    private ArrayList<Bills> paid = new ArrayList<>();
    private ArrayList<Bills> expired = new ArrayList<>();
    
    private BillManager() {
        factoryDAO = FactoryDAO.getInstance();
        billsDAO = factoryDAO.getBillsDAO();
    }
    
    public static BillManager getInstance(){
        if (INSTANCE==null){
            INSTANCE=new BillManager();
            return INSTANCE;
        }
        return INSTANCE;
    }

    public ArrayList<Bills> getIssued() {
        return issued;
    }

    public ArrayList<Bills> getPaid() {
        return paid;
    }

    public ArrayList<Bills> getExpired() {
        return expired;
    }               
        


    public void create(
        String RF,
         String billNumber,
         int amount,
         Business issuer,
         Customer customer,
         LocalDate issueDate,
         LocalDate dueDate) {
        Bills bill = new Bills(RF , billNumber, amount, issuer , customer , issueDate , dueDate, BillStatus.PENDING);
        issued.add(bill);
    }

    public void billsPayed(Bills bill) {
        paid.add(bill);
        issued.remove(bill);
    }

    public void deleteBill(String RF) {
        Bills bill = findBill(RF);
        issued.remove(bill);
        paid.remove(bill);
    }

    public Bills findBill(String RF) {
        for (Bills bill : issued) {
            if (bill.getRF().equals(RF))
                return bill;
        }
        return null;
    }

    public ArrayList<Bills> findMyBills(String customer) {
        ArrayList<Bills> myBills = new ArrayList<>();
        for (Bills bill : issued) {
            if(bill.getCustomer().equals(customer))
                myBills.add(bill);
        }
        return myBills;
    }

    public ArrayList<Bills> findCompanyIssuedBills(String issuer) {
        ArrayList<Bills> companyBills = new ArrayList<>();
        for (Bills bill : issued) {
            if (bill.getIssuer().equals(issuer))
                companyBills.add(bill);
        }
        return companyBills;
    }

    public ArrayList<Bills> findCompanyPayedBills(String issuer) {
        ArrayList<Bills> companyBills = new ArrayList<>();
        for (Bills bill : paid) {
            if (bill.getIssuer().equals(issuer))
                companyBills.add(bill);
        }
        return companyBills;
    }

//    public void restoreEachDay(){
//        ArrayList<Bills> tempBills = new ArrayList<>();
//        String fileName;
//        File[] files = new File("./data/bills/").listFiles();
//        if (files == null) return;
//        for (File file : files) {
//            fileName = file.getName().substring(0,file.getName().length()-4);
//            if (fileName.startsWith(".")||fileName.equals("issued")||fileName.equals("paid")) continue;
//            if (LocalDate.parse(fileName).isBefore(MainMenu.today)){
//                Storager.getInstance().load(tempBills,"./data/bills/"+file.getName());
//                for (Bills bill : tempBills.getList()) {
//                    issued.getList().add(bill);
//                }
//
//                tempBills.getList().clear(); // newLine
//                file.delete();
//            }
//        }
//    }

    @Override
    public void restore() {
       billsDAO.loadBills();
       issued =(ArrayList<Bills>) billsDAO.getIssued();
       paid =(ArrayList<Bills>) billsDAO.getPaid();
       expired =(ArrayList<Bills>) billsDAO.getExpired();
    }
    @Override
    public void save(){
        billsDAO.saveBills(issued);
        billsDAO.appendBills(paid);
        billsDAO.appendBills(expired);
    }

//    @Override
//    public void save() {
//        List<ArrayList<Bills>> sameDateCollections = new ArrayList<>();
//        ArrayList<Bills> removeList = new ArrayList<>();
//        boolean isAdded;
//        sameDateCollections.add(new ArrayList<Bills>());
//        sameDateCollections.get(0).getList().add(issued.getList().get(0));
//        for (Bills bill : issued.getList()) {
//            isAdded = false;
//            if(bill.equals(issued.getList().get(0)))
//                continue;
//            for (ArrayList<Bills> sameDateCollection : sameDateCollections) {
//                if (sameDateCollection.getList().get(0).getIssueDate().equals(bill.getIssueDate())) {
//                    sameDateCollection.getList().add(bill);
//                    if (bill.getIssueDate().isAfter(MainMenu.today)) removeList.getList().add(bill);
//                    isAdded = true;
//                    break;
//                }
//            }
//            if (!isAdded){
//                sameDateCollections.add(new ArrayList<Bills>());
//                sameDateCollections.get(sameDateCollections.size()-1).getList().add(bill);
//                if (bill.getIssueDate().isAfter(MainMenu.today)) removeList.getList().add(bill);
//            }
//        }
//        for (Bills bill : removeList.getList()) {
//            issued.getList().remove(bill);
//        }
//        for (ArrayList<Bills> sameDateCollection : sameDateCollections) {
//            if (sameDateCollection.getList().get(0).getIssueDate().isAfter(MainMenu.today))
//                Storager.getInstance().save(sameDateCollection,"./data/bills/"+sameDateCollection.getList().get(0).getIssueDate()+".csv", false);
//        }
//        Storager.getInstance().save(issued,"./data/bills/issued.csv", false);
//        Storager.getInstance().save(paid,"./data/bills/paid.csv", false);
//    }

}
