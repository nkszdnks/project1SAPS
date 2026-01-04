package DataAccessObjects;

import Entities.Users.IncomingPayment;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IncomingPaymentDAO {
    private final String filePath;

    public IncomingPaymentDAO(String filePath) { this.filePath = filePath; }

    public List<IncomingPayment> loadAll() {
        List<IncomingPayment> list = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null)
                if (!line.trim().isEmpty()) list.add(IncomingPayment.unmarshal(line));
        } catch (IOException e) { throw new RuntimeException(e); }
        return list;
    }

    public void saveAll(List<IncomingPayment> payments) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (IncomingPayment p : payments) pw.println(p.marshal());
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}