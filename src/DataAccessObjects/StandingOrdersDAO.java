package DataAccessObjects;

import Entities.Accounts.Statements.FailedOrderStatement;
import Entities.Accounts.Statements.Statement;
import Entities.StandingOrders.OrderStatus;
import Entities.StandingOrders.StandingOrder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StandingOrdersDAO {
    private String[] standingOrdersFiles;
    List<StandingOrder> active = new ArrayList<>();
    List<StandingOrder> expired = new ArrayList<>();
    List<StandingOrder> failed = new ArrayList<>();
    public StandingOrdersDAO(String[] standingOrdersFile) {
        this.standingOrdersFiles = standingOrdersFile;
    }

    public List<StandingOrder> getActive() {
        return active;
    }

    public List<StandingOrder> getExpired() {
        return expired;
    }

    public List<StandingOrder> getFailed() {
        return failed;
    }

    public ArrayList<StandingOrder> loadActiveStandingOrders() {
        ArrayList<StandingOrder> activeStandingOrders = new ArrayList<>();
        File f = new File(standingOrdersFiles[0]);
        if (!f.exists()) return activeStandingOrders;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            //boolean first = true;
            while ((line = br.readLine()) != null) {
                //  if (first) {
                //    first = false;
                //   continue;
                //} // skip header
                if (line.trim().isEmpty()) continue;
                StandingOrder acc = StandingOrder.unmarshal(line);
                activeStandingOrders.add(acc);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return activeStandingOrders;
    }

    public void saveActiveStandingOrders(List<StandingOrder> StandingOrders) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(standingOrdersFiles[0]))) {
            //pw.println("AccountID,IBAN,InterestRate,Balance,DateCreated,AccountType,MainOwnerID,SecondaryOwnerIDs,MaintenanceFee");
            for (StandingOrder a : StandingOrders) {
                pw.println(a.marshal());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<FailedOrderStatement> loadFailedStandingOrders() {
        ArrayList<FailedOrderStatement> activeStandingOrders = new ArrayList<>();
        File f = new File(standingOrdersFiles[2]);
        if (!f.exists()) return activeStandingOrders;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            //boolean first = true;
            while ((line = br.readLine()) != null) {
                //  if (first) {
                //    first = false;
                //   continue;
                //} // skip header
                if (line.trim().isEmpty()) continue;
                FailedOrderStatement acc = FailedOrderStatement.unmarshal(line);
                activeStandingOrders.add(acc);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return activeStandingOrders;
    }

    public void saveFailedStandingOrders(List<FailedOrderStatement> StandingOrders) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(standingOrdersFiles[2]))) {
            //pw.println("AccountID,IBAN,InterestRate,Balance,DateCreated,AccountType,MainOwnerID,SecondaryOwnerIDs,MaintenanceFee");
            for (FailedOrderStatement a : StandingOrders) {
                pw.println(a.marshal());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<StandingOrder> loadExpiredStandingOrders() {
        ArrayList<StandingOrder> activeStandingOrders = new ArrayList<>();
        File f = new File(standingOrdersFiles[1]);
        if (!f.exists()) return activeStandingOrders;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            //boolean first = true;
            while ((line = br.readLine()) != null) {
                //  if (first) {
                //    first = false;
                //   continue;
                //} // skip header
                if (line.trim().isEmpty()) continue;
                StandingOrder acc = StandingOrder.unmarshal(line);
                activeStandingOrders.add(acc);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return activeStandingOrders;
    }

    public void saveExpriredStandingOrders(List<StandingOrder> StandingOrders) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(standingOrdersFiles[1]))) {
            //pw.println("AccountID,IBAN,InterestRate,Balance,DateCreated,AccountType,MainOwnerID,SecondaryOwnerIDs,MaintenanceFee");
            for (StandingOrder a : StandingOrders) {
                a.setStatus(OrderStatus.EXPIRED);
                pw.println(a.marshal());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
