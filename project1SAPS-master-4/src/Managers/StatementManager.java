package Managers;

import DataAccessObjects.FactoryDAO;
import DataAccessObjects.StatementDAO;
import Entities.Accounts.Statements.Statement;
import Managers.Manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StatementManager implements Manager {
    private static  StatementManager INSTANCE;
    private FactoryDAO factoryDAO;
    private StatementDAO statementDAO;
    private ArrayList<Statement> statements = new ArrayList<Statement>();

    private StatementManager() {
        factoryDAO = FactoryDAO.getInstance();
        statementDAO = factoryDAO.getStatementsDAO();
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    public static StatementManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatementManager();
            return INSTANCE;
        }
        return INSTANCE;
    }

    public void createStatement(Statement statement) {
        statements.add(statement);
    }

    public Statement[] getStatementsByIban(String iban) {
        ArrayList<Statement> statementsList = new ArrayList<>();
        for (Statement statement : statements) {
            if(statement.getIbansInvolved()[0].equals(iban)) {//Needs check
                statementsList.add(statement);
            }
        }
        return statementsList.toArray(new Statement[statementsList.size()]);
    }

    @Override
    public void restore() {
        statements =(ArrayList<Statement>)statementDAO.loadStatements();
    }
//    @Override
//    public void restore() {
//        MyCollection<Statement> tempStatements = new MyCollection<>();
//        File[] files = new File("./data/statements/").listFiles();
//        if (files == null) return;
//        for(File file : files) {
//            if(file.getName().startsWith(".")) continue;
//            Storager.getInstance().load(tempStatements,"./data/statements/"+file.getName());
//            for(Statement statement : tempStatements.getList()) {
//                statements.getList().add(statement);
//            }
//            tempStatements.getList().clear(); // newLine
//        }
//    }

    @Override
    public void save() {
        statementDAO.saveStatements(statements);
    }


//    @Override
//    public void save() {
//        if (statements.getList().isEmpty()) {
//            System.out.println("Nothing to do");
//            return;
//        }
//        List<MyCollection<Statement>> sameIbanCollections = new ArrayList<>();
//        boolean isAdded;
//        sameIbanCollections.add(new MyCollection<Statement>());
//        sameIbanCollections.get(0).getList().add(statements.getList().get(0));
//        for (Statement statement : statements.getList()) {
//            isAdded = false;
//            if(statement.equals(statements.getList().get(0)))
//                continue;
//            for (MyCollection<Statement> sameIbanCollection : sameIbanCollections) {
//                if (sameIbanCollection.getList().get(0).getIbansInvolved()[0].equals(statement.getIbansInvolved()[0])) {
//                    sameIbanCollection.getList().add(statement);
//                    isAdded = true;
//                    break;
//                }
//            }
//            if (!isAdded){
//                sameIbanCollections.add(new MyCollection<Statement>());
//                sameIbanCollections.get(sameIbanCollections.size()-1).getList().add(statement); // newLine
//            }
//        }
//        for (MyCollection<Statement> sameIbanCollection : sameIbanCollections) {
//            Storager.getInstance().save(sameIbanCollection,"./data/statements/iban"+sameIbanCollection.getList().get(0).getIbansInvolved()[0]+".csv", true); // newLine
//        }
//    }
}