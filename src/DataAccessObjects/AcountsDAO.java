package DataAccessObjects;

import Entities.Accounts.BankAcount;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AcountsDAO {
    private String acountsFile;
    public AcountsDAO(String acountsFile) {
        this.acountsFile = acountsFile;
    }

    public List<BankAcount> loadAccounts() {
        List<BankAcount> list = new ArrayList<>();
        File f = new File(acountsFile);
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
                BankAcount acc = BankAcount.unmarshal(line);
                list.add(acc);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void saveAccounts(List<BankAcount> accounts) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(acountsFile))) {
            //pw.println("AccountID,IBAN,InterestRate,Balance,DateCreated,AccountType,MainOwnerID,SecondaryOwnerIDs,MaintenanceFee");
            for (BankAcount a : accounts) {
                pw.println(a.marshal());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
