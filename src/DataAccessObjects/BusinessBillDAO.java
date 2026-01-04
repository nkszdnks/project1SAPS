package DataAccessObjects;

import Entities.Users.BusinessBill;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessBillDAO {
    private final String filePath;

    public BusinessBillDAO(String filePath) { this.filePath = filePath; }

    public List<BusinessBill> loadAll() {
        List<BusinessBill> list = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null)
                if (!line.trim().isEmpty()) list.add(BusinessBill.unmarshal(line));
        } catch (IOException e) { throw new RuntimeException(e); }
        return list;
    }

    public void saveAll(List<BusinessBill> bills) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (BusinessBill b : bills) pw.println(b.marshal());
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}