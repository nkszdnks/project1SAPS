package swinglab.View;

import Entities.Users.Customer;
import Managers.*;
import swinglab.Contollers.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BankFrame extends JFrame implements ActionListener {

    JMenuItem miLogin, miViewAllBills, miViewAllStandingOrders, miSimulatetTime, miDashboard, miBusinessDashboard, miAdminDashboard,
            miAccounts, miTransfers, miPayments, miAbout, miExit, miLogout,  miViewAccounts, miViewStatements,
            miRequests, miIssueBill, miCompanyReceipts;
    JMenu nav;
    JLabel date;
    JLabel user;

    public BankFrame() {
        setTitle("Bank of TUC e-Banking");
        setSize(900, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        JMenuBar bar = new JMenuBar();
        nav = new JMenu("Navigate");
        date = new JLabel("Date:" + AppMediator.getToday());
        user = new JLabel("  User: - ");

        // Μενού items
        miLogin = new JMenuItem("Login");
        miDashboard = new JMenuItem("Dashboard");
        miBusinessDashboard = new JMenuItem("Dashboard");
        miAdminDashboard = new JMenuItem("Dashboard");
        miAccounts = new JMenuItem("Accounts");
        miTransfers = new JMenuItem("Transfers");
        miPayments = new JMenuItem("Payments");

        miViewAccounts = new JMenuItem("View Accounts");
        miViewStatements = new JMenuItem("View Statements");
        miRequests = new JMenuItem("Requests");
        miViewAllBills = new JMenuItem("View All Bills");
        miSimulatetTime = new JMenuItem("Simulate Time");
        miViewAllStandingOrders = new JMenuItem("View All Standing Orders");
        miExit = new JMenuItem("Exit");
        miLogout = new JMenuItem("Logout");
        miAbout = new JMenuItem("About");
        miIssueBill = new JMenuItem("Issue Bill");
        miCompanyReceipts = new JMenuItem("Company Receipts");

        // Αρχική ορατότητα
        miDashboard.setVisible(false);
        miBusinessDashboard.setVisible(false);
        miAdminDashboard.setVisible(false);
        miAccounts.setVisible(false);
        miTransfers.setVisible(false);
        miPayments.setVisible(false);

        miViewAccounts.setVisible(false);
        miViewStatements.setVisible(false);
        miRequests.setVisible(false);
        miViewAllBills.setVisible(false);
        miSimulatetTime.setVisible(false);
        miViewAllStandingOrders.setVisible(false);
        miLogout.setVisible(false);
        miIssueBill.setVisible(false);
        miCompanyReceipts.setVisible(false);

        // Προσθήκη στο μενού
        nav.add(miLogin);
        nav.add(miDashboard);
        nav.add(miBusinessDashboard);
        nav.add(miAdminDashboard);
        nav.add(miAccounts);
        nav.add(miTransfers);
        nav.add(miPayments);

        nav.add(miIssueBill);
        nav.add(miCompanyReceipts);
        nav.add(miViewAccounts);
        nav.add(miViewStatements);
        nav.add(miRequests);
        nav.add(miViewAllBills);
        nav.add(miViewAllStandingOrders);
        nav.add(miSimulatetTime);
        nav.addSeparator();
        nav.add(miLogout);
        nav.add(miExit);

        bar.add(nav);
        bar.add(date);
        bar.add(user);
        setJMenuBar(bar);

        // Αρχικοποίηση δεδομένων
        UserManager.getInstance().restore();
        AccountManager.getInstance().restore();
        AccountManager.getInstance().getBankAccounts().add(AppMediator.getBankOfTucAccount());
        BillManager.getInstance().restore();
        StatementManager.getInstance().restore();
        AdminRequestsManager.getInstance().restore();
        StandingOrderManager.getInstance().restore();

        buildPanels();
        AppMediator.setBank(this);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                UserManager.getInstance().save();
                AccountManager.getInstance().save();
                StatementManager.getInstance().save();
                BillManager.getInstance().save();
                AdminRequestsManager.getInstance().save();
                StandingOrderManager.getInstance().save();
                System.out.println("Data saved before exit.");
            }
        });
    }

    public void enableUserMenu() {
        miDashboard.setVisible(true);
        miAccounts.setVisible(true);
        miTransfers.setVisible(true);
        miPayments.setVisible(true);

        miLogin.setVisible(false);
        miLogout.setVisible(true);
    }

    public void enableBusinessUserMenu() {
        miBusinessDashboard.setVisible(true);
        miAccounts.setVisible(true);
        miTransfers.setVisible(true);
        miPayments.setVisible(true);

        miLogin.setVisible(false);
        miLogout.setVisible(true);
        miIssueBill.setVisible(true);
        miCompanyReceipts.setVisible(true);
    }

    public void enableAdminUserMenu() {
        miAdminDashboard.setVisible(true);
        miViewAccounts.setVisible(true);
        miViewStatements.setVisible(true);
        miRequests.setVisible(true);
        miLogin.setVisible(false);
        miLogout.setVisible(true);
        miSimulatetTime.setVisible(true);
        miViewAllStandingOrders.setVisible(true);
        miViewAllBills.setVisible(true);
    }

    public void disableUserMenu() {
        miDashboard.setVisible(false);
        miBusinessDashboard.setVisible(false);
        miAdminDashboard.setVisible(false);
        miAccounts.setVisible(false);
        miTransfers.setVisible(false);
        miPayments.setVisible(false);

        miViewAccounts.setVisible(false);
        miViewStatements.setVisible(false);
        miRequests.setVisible(false);
        miViewAllBills.setVisible(false);
        miSimulatetTime.setVisible(false);
        miViewAllStandingOrders.setVisible(false);
        miLogout.setVisible(false);
        miIssueBill.setVisible(false);
        miCompanyReceipts.setVisible(false);
        miLogin.setVisible(true);
    }

    public void buildPanels() {
        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);
        AppMediator.setCardLayout(cardLayout);
        AppMediator.setCards(cards);

        IssueBillPanel issueBillPanel = new IssueBillPanel();
        CompanyReceiptsPanel companyReceiptsPanel = new CompanyReceiptsPanel();

        LoginPanel loginPanel = new LoginPanel();
        DashboardPanel dashboardPanel = new DashboardPanel();
        BusinessDashboardPanel businessDashboardPanel = new BusinessDashboardPanel();
        AdminDashboardPanel adminDashboardPanel = new AdminDashboardPanel();
        AccountsPanel accountsPanel = new AccountsPanel();
        AccountsController.getInstance().setView(accountsPanel);

        ActiveStandingOrdersPanel activeStandingOrdersPanel = new ActiveStandingOrdersPanel();
        ActiveOrdersController.getInstance().setView(activeStandingOrdersPanel);
        swinglab.FailedOrdersPanel failedOrdersPanel = new swinglab.FailedOrdersPanel();
        FailedOrdersController.getInstance().setView(failedOrdersPanel);

        AdminRequestsPanel adminRequestsPanel = new AdminRequestsPanel();
        AdminRequestsController.getInstance().setView(adminRequestsPanel);
        AboutPanel aboutPanel = new AboutPanel();
        CoOwnersPanel coOwnersPanel = new CoOwnersPanel();
        CoOwnersController.getInstance().setView(coOwnersPanel);
        OpenAcountPanel newAcountPanel = new OpenAcountPanel();
        OpenAccountController.getInstance().setView(newAcountPanel);
        TransactionHistoryPanel transactionPanel = new TransactionHistoryPanel();
        TransactionHistoryController.getInstance().setView(transactionPanel);
        TransfersPanel transfersPanel = new TransfersPanel();
        IntraBankTransferPanel intraBankTransferPanel = new IntraBankTransferPanel();
        InterBankTransferPanel interBankTransferPanel = new InterBankTransferPanel();
        IntraBankTransferController.getInstance().setViewIntra(intraBankTransferPanel);
        IntraBankTransferController.getInstance().setViewInter(interBankTransferPanel);

        SimulateTimePanel simulateTimePanel = new SimulateTimePanel();
        SimulateTimeController simulateTimeController = new SimulateTimeController(simulateTimePanel);
        AllStatementsPanel allStatementsPanel = new AllStatementsPanel();
        ViewAllBillsPanel allBillsPanel = new ViewAllBillsPanel();
        AllStatementsController.getInstance().setView(allStatementsPanel);
        AllStatementsController.getInstance().setViewBills(allBillsPanel);

        StandingTransferOrderPanel standingTransferOrderPanel = new StandingTransferOrderPanel();
        StandingPaymentOrderPanel standingPaymentOrderPanel = new StandingPaymentOrderPanel();
        StandingTransferOrderController.getInstance().setViewTransfer(standingTransferOrderPanel);
        StandingTransferOrderController.getInstance().setViewPayment(standingPaymentOrderPanel);

        cards.add(loginPanel, "login");
        cards.add(allStatementsPanel, "allStatements");
        cards.add(allBillsPanel, "allBills");
        cards.add(simulateTimePanel, "simulateTime");
        cards.add(adminRequestsPanel, "adminRequests");
        cards.add(businessDashboardPanel, "businessDashboard");
        cards.add(adminDashboardPanel, "adminDashboard");
        cards.add(dashboardPanel, "dashboard");
        cards.add(accountsPanel, "accounts");
        cards.add(aboutPanel, "about");
        cards.add(coOwnersPanel, "coOwners");
        cards.add(newAcountPanel, "newAcount");
        cards.add(transactionPanel, "transactionsPanel");
        cards.add(transfersPanel, "transfersPanel");
        cards.add(interBankTransferPanel, "interbank");
        cards.add(intraBankTransferPanel, "intrabank");

        DepositMoneyPanel depositMoneyPanel = new DepositMoneyPanel();
        DepositMoneyController.getInstance().setView(depositMoneyPanel);
        WithdrawMoneyPanel withdrawMoneyPanel = new WithdrawMoneyPanel();
        WithdrawlsController.getInstance().setView(withdrawMoneyPanel);
        cards.add(depositMoneyPanel, "deposit");
        cards.add(withdrawMoneyPanel, "withdraw");
        cards.add(standingTransferOrderPanel, "standingTransferOrder");
        NewCoOwnerPanel newCoOwnerPanel = new NewCoOwnerPanel();
        NewCoOwnerController.getInstance().setView(newCoOwnerPanel);
        cards.add(newCoOwnerPanel, "newCoOwner");
        cards.add(new ChangePasswordPanel(), "changePersonalDetails");
        cards.add(new PaymentsPanel(), "payments");
        PayBillsPanel payBillsPanel = new PayBillsPanel();
        PayBillsController.getInstance().setView(payBillsPanel);
        cards.add(payBillsPanel, "payBills");
        cards.add(standingPaymentOrderPanel, "standingPaymentOrder");
        cards.add(new StandingPaymentHistoryPanel(), "standingPaymentHistory");
        cards.add(activeStandingOrdersPanel, "activeStandingOrders");
        cards.add(failedOrdersPanel, "failedOrders");
        cards.add(new EditStandingPaymentPanel(), "editStandingPayment");
        cards.add(new EditStandingTransferPanel(), "editStandingTransfer");
        UsersPanel usersPanel = new UsersPanel();
        UsersController.getInstance().setView(usersPanel);
        cards.add(usersPanel, "allUsers");


        cards.add(issueBillPanel, "issueBill");
        cards.add(companyReceiptsPanel, "companyReceipts");

        add(cards);

        // Listeners
        miLogin.addActionListener(this);
        miDashboard.addActionListener(this);
        miAccounts.addActionListener(this);
        miViewAccounts.addActionListener(this);
        miAdminDashboard.addActionListener(this);
        miAbout.addActionListener(this);
        miPayments.addActionListener(this);
        miTransfers.addActionListener(this);
        miLogout.addActionListener(this);
        miSimulatetTime.addActionListener(this);
        miViewAllBills.addActionListener(this);
        miRequests.addActionListener(this);
        miViewAllStandingOrders.addActionListener(this);
        miViewStatements.addActionListener(this);
        miExit.addActionListener(this);

        miIssueBill.addActionListener(this);
        miCompanyReceipts.addActionListener(this);

        AppMediator.setAllCardsColor(new Color(230, 240, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == miLogin)
            AppMediator.getCardLayout().show(AppMediator.getCards(), "login");
        else if (e.getSource() == miDashboard)
            AppMediator.goToHomeDashboard();
        else if (e.getSource() == miAccounts) {
            AccountsController.getInstance().setModel((Customer) AppMediator.getUser());
            AppMediator.getCardLayout().show(AppMediator.getCards(), "accounts");
        } else if (e.getSource() == miViewStatements) {
            AllStatementsController.getInstance().loadStatements();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "allStatements");
        } else if (e.getSource() == miViewAccounts) {
            AccountsController.getInstance().setModel(null);
            AccountsController.getInstance().loadAllAccounts();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "accounts");
        } else if (e.getSource() == miRequests) {
            AdminRequestsController.getInstance().loadPendingRequests();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "adminRequests");
        } else if (e.getSource() == miAbout)
            AppMediator.getCardLayout().show(AppMediator.getCards(), "about");
        else if (e.getSource() == miTransfers)
            AppMediator.getCardLayout().show(AppMediator.getCards(), "transfersPanel");
        else if (e.getSource() == miPayments)
            AppMediator.getCardLayout().show(AppMediator.getCards(), "payments");
        else if (e.getSource() == miLogout) {
            disableUserMenu();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "login");
        } else if (e.getSource() == miViewAllStandingOrders) {
            ActiveOrdersController.getInstance().setModel(null);
            ActiveOrdersController.getInstance().loadAllOrders();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "activeStandingOrders");
        } else if (e.getSource() == miSimulatetTime) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "simulateTime");
        } else if (e.getSource() == miViewAllBills) {
            AllStatementsController.getInstance().loadBills();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "allBills");
        } else if (e.getSource() == miExit) {
            UserManager.getInstance().save();
            AccountManager.getInstance().save();
            StatementManager.getInstance().save();
            BillManager.getInstance().save();
            AdminRequestsManager.getInstance().save();
            StandingOrderManager.getInstance().save();
            System.exit(0);

        } else if (e.getSource() == miIssueBill) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "issueBill");
        } else if (e.getSource() == miCompanyReceipts) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "companyReceipts");
        }
    }
}