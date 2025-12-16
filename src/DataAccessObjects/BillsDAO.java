package DataAccessObjects;

import Entities.Users.BillStatus;
import Entities.Users.Bills;

import Entities.Accounts.Statements.Statement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BillsDAO {

    private final String filePath;
    List<Bills> issued = new ArrayList<>();
    List<Bills> paid = new ArrayList<>();
    List<Bills> expired = new ArrayList<>();

    public BillsDAO(String filePath) {
        this.filePath = filePath;
    }

    // ---------------------------------------------------------
    // Load all statements
    // ---------------------------------------------------------
    public void loadBills() {

        File file = new File(filePath);

        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Bills s = Bills.unmarshal(line);
                switch (s.getStatus()) {
                    case BillStatus.PENDING:
                        issued.add(s);
                        break;
                    case BillStatus.PAID:
                        paid.add(s);
                        break;
                    case BillStatus.EXPIRED:
                        expired.add(s);
                        break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading statements CSV", e);
        }


    }

    // ---------------------------------------------------------
    // Save all statements (overwrite file)
    // ---------------------------------------------------------
    public void saveBills(List<Bills> bills) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

            for (Bills b : bills) {
                pw.println(b.marshal());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error writing statements CSV", e);
        }
    }

    public void appendBills(List<Bills> Bills) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
            for (Bills b : Bills) {
                pw.println(b.marshal());
            }
            }
        catch (IOException e) {
            throw new RuntimeException("Error appending statement to CSV", e);
        }
    }

    public List<Bills> getIssued() {
        return issued;
    }

    public List<Bills> getPaid() {
        return paid;
    }
    public List<Bills> getExpired() {
        return expired;
    }
}
