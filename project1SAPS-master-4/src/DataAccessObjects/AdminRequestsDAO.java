package DataAccessObjects;

import Entities.Accounts.BankAcount;
import Entities.AdminRequests.AdminRequest;
import Entities.Users.Admin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRequestsDAO {
    private String requestsFile;
    public AdminRequestsDAO(String requestsFile) {
        this.requestsFile = requestsFile;
    }

    public List<AdminRequest> loadRequests() {
        List<AdminRequest> list = new ArrayList<>();
        File f = new File(requestsFile);
        if (!f.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            //boolean first = true;
            while ((line = br.readLine()) != null) {
                //  if (first) {
                //    first = false;
                //   continue;
                //} // skip header
                if (line.trim().isEmpty()) continue;
                AdminRequest ar = AdminRequest.unmarshal(line);
                list.add(ar);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void saveAccounts(List<AdminRequest> requests) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(requestsFile))) {
            //pw.println("AccountID,IBAN,InterestRate,Balance,DateCreated,AccountType,MainOwnerID,SecondaryOwnerIDs,MaintenanceFee");
            for (AdminRequest req : requests) {
                pw.println(req.marshal());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
