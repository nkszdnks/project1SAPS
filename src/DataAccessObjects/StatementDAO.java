package DataAccessObjects;


import Entities.Accounts.BankAcount;
import Entities.Accounts.Statements.Statement;
import Managers.AccountManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StatementDAO {

    private final String filePath;

    public StatementDAO(String filePath) {
        this.filePath = filePath;
    }

    // ---------------------------------------------------------
    // Load all statements
    // ---------------------------------------------------------
    public List<Statement> loadStatements() {
        List<Statement> list = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Statement s = Statement.unmarshal(line);
                AccountManager.getInstance().findAccountByIBAN(s.getIbansInvolved()[0]).addStatements(s);
                if(!s.getIbansInvolved()[1].isEmpty()) {
                    if(AccountManager.getInstance().findAccountByIBAN(s.getIbansInvolved()[1])!=null)
                        AccountManager.getInstance().findAccountByIBAN(s.getIbansInvolved()[1]).addStatements(s);
                }
                list.add(s);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading statements CSV", e);
        }

        return list;
    }

    // ---------------------------------------------------------
    // Save all statements (overwrite file)
    // ---------------------------------------------------------
    public void saveStatements(List<Statement> statements) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

            for (Statement s : statements) {
                pw.println(s.marshal());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error writing statements CSV", e);
        }
    }

    // ---------------------------------------------------------
    // Append a single statement
    // ---------------------------------------------------------
    public void appendStatement(Statement statement) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
            pw.println(statement.marshal());
        } catch (IOException e) {
            throw new RuntimeException("Error appending statement to CSV", e);
        }
    }
}
