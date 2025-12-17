package DataAccessObjects;

//import Managers.UserManager;

public class FactoryDAO {
    private static  FactoryDAO INSTANCE ;
    private static final String usersFile = "users.csv";
    private static final String acountsFile = "accounts.csv";
    private static final String statementsFile = "statements.csv";
    private static final String[] standingOrdersFiles = {"activeOrders.csv","pausedOrders.csv","canceledOrders.csv"};
    private static final String billsFile = "bills.csv";
    private static final String requestsFile = "requests.csv";

    private FactoryDAO() {
    }
    public static FactoryDAO getInstance() {
        if(INSTANCE == null) {
           INSTANCE = new FactoryDAO();
           return INSTANCE;
        }
        return INSTANCE;
    }

    public UsersDAO getUsersDAO() {
        UsersDAO usersDAO = new UsersDAO(usersFile);
        return usersDAO;
    }
    public AcountsDAO getAcountsDAO() {
        AcountsDAO acountsDAO = new AcountsDAO(acountsFile);
        return acountsDAO;
    }

    public StatementDAO getStatementsDAO() {
        StatementDAO statemnentsDAO = new StatementDAO(statementsFile);
        return statemnentsDAO;
    }

    public StandingOrdersDAO getStandingOrdersDAO() {
        StandingOrdersDAO standingOrdersDAO = new StandingOrdersDAO(standingOrdersFiles);
        return standingOrdersDAO;
    }

    public BillsDAO getBillsDAO() {
        BillsDAO billsDAO = new BillsDAO(billsFile);
        return billsDAO;
    }

    public AdminRequestsDAO getAdminRequestsDAO() {
        AdminRequestsDAO adminsDAO = new AdminRequestsDAO(requestsFile);
        return adminsDAO;
    }

}
