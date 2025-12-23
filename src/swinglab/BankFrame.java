package swinglab;

import Entities.Users.Customer;
import Managers.*;
import swinglab.Contollers.*;
import swinglab.View.AdminRequestsPanel;

import swinglab.View.AllStatementsPanel;
import swinglab.View.SimulateTimePanel;
import swinglab.View.ViewAllBillsPanel;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class BankFrame extends JFrame implements ActionListener{
	JMenuItem miLogin,miDashboard,miBusinessDashboard,miAdminDashboard,miAccounts,miTransfers,miPayments,miAbout,miExit,miLogout,miMyBills,miViewAccounts,miViewStatements,miRequests;
	JMenu nav;
    JLabel date;


	public BankFrame() {
        setTitle("Bank of TUC e-Banking");
        setSize(900, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setVisible(true);

        // --- Menu bar ---
        JMenuBar bar = new JMenuBar();
        nav = new JMenu("Navigate");
        date  = new JLabel("Date:"+String.valueOf(AppMediator.getToday()));
        miLogin = new JMenuItem("Login");
        miDashboard = new JMenuItem("Dashboard");
        miAccounts = new JMenuItem("Accounts");
        miTransfers = new JMenuItem("Transfers");
        miPayments = new JMenuItem("Payments");
        miAbout = new JMenuItem("About");
        miLogout = new JMenuItem("Logout");
        miMyBills = new JMenuItem("My Bills");
        miViewAccounts = new JMenuItem("View Accounts");
        miViewStatements = new JMenuItem("View Statements");
        miRequests = new JMenuItem("Requests");
        miAdminDashboard = new JMenuItem("Dashboard");
        miBusinessDashboard = new JMenuItem("Dashboard");
        miExit = new JMenuItem("Exit");

        miDashboard.setVisible(false);
        miBusinessDashboard.setVisible(false);
        miAdminDashboard.setVisible(false);
        miRequests.setVisible(false);
        miViewAccounts.setVisible(false);
        miViewStatements.setVisible(false);
        miAccounts.setVisible(false);
        miTransfers.setVisible(false);
        miPayments.setVisible(false);
        miLogout.setVisible(false);
        miMyBills.setVisible(false);

        nav.add(miLogin);
        nav.add(miDashboard);
        nav.add(miAccounts);
        nav.add(miAbout);
        nav.add(miTransfers);
        nav.add(miPayments);
        nav.add(miMyBills);
        nav.add(miViewAccounts);
        nav.add(miViewStatements);
        nav.add(miRequests);
        nav.add(miAdminDashboard);
        nav.add(miBusinessDashboard);
        nav.addSeparator();
        nav.add(miLogout);
        nav.add(miExit);


        bar.add(nav);
        bar.add(date);
        setJMenuBar(bar);

        //
        UserManager.getInstance().restore();
        AccountManager.getInstance().restore();
        AccountManager.getInstance().getBankAccounts().add(AppMediator.getBankOfTucAccount());
        BillManager.getInstance().restore();
        StatementManager.getInstance().restore();
        AdminRequestsManager.getInstance().restore();
        StandingOrderManager.getInstance().restore();


        //
        buildPanels();

        AppMediator.setBank(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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
        miMyBills.setVisible(false);
        miLogout.setVisible(true);
    }
    public void enableBusinessUserMenu() {
        miBusinessDashboard.setVisible(true);
        miAccounts.setVisible(true);
        miTransfers.setVisible(true);
        miPayments.setVisible(true);
        miLogin.setVisible(false);
        miMyBills.setVisible(true);
        miLogout.setVisible(true);
    }
    public void enableAdminUserMenu() {
        miAdminDashboard.setVisible(true);
        miViewAccounts.setVisible(true);
        miViewStatements.setVisible(true);
        miRequests.setVisible(true);
        miLogin.setVisible(false);
        miLogout.setVisible(true);
    }

	public void disableUserMenu() {
		miDashboard.setVisible(false);
        miAdminDashboard.setVisible(false);
        miBusinessDashboard.setVisible(false);
        miViewAccounts.setVisible(false);
        miViewStatements.setVisible(false);
        miRequests.setVisible(false);
        miAccounts.setVisible(false);
        miTransfers.setVisible(false);
        miPayments.setVisible(false);
        miLogin.setVisible(true);
        miLogout.setVisible(false);
	}

    public void buildPanels() {
    	// --- Cards container (pages) ---
    	CardLayout cardLayout = new CardLayout();
    	JPanel cards = new JPanel(cardLayout);
    	AppMediator.setCardLayout(cardLayout);
    	AppMediator.setCards(cards);

    	// create panels (we'll implement below)
    	LoginPanel loginPanel = new LoginPanel();
        loginPanel.setBackground(Color.pink);
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
    	TransfersPanel  transfersPanel = new TransfersPanel();
        IntraBankTransferPanel intraBankTransferPanel= new IntraBankTransferPanel();
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




    	// register the panels with names

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
    	cards.add( interBankTransferPanel, "interbank");
    	cards.add(intraBankTransferPanel, "intrabank");
        DepositMoneyPanel depositMoneyPanel = new DepositMoneyPanel();
        DepositMoneyController depositMoneyController = new DepositMoneyController(depositMoneyPanel);
        WithdrawMoneyPanel withdrawMoneyPanel = new WithdrawMoneyPanel();
        WithdrawlsController withdrawlsController = new WithdrawlsController(withdrawMoneyPanel);
        cards.add(depositMoneyPanel, "deposit");
        cards.add(withdrawMoneyPanel, "withdraw");
        cards.add(standingTransferOrderPanel, "standingTransferOrder");
        NewCoOwnerPanel newCoOwnerPanel = new NewCoOwnerPanel();
        NewCoOwnerController.getInstance().setView(newCoOwnerPanel);
    	cards.add(newCoOwnerPanel, "newCoOwner");
    	cards.add(new ChangePasswordPanel(), "changePersonalDetails");
    	cards.add(new PaymentsPanel(), "payments");
        PayBillsPanel  payBillsPanel = new PayBillsPanel();
        PayBillsController.getInstance().setView(payBillsPanel);
    	cards.add(payBillsPanel, "payBills");
    	cards.add( standingPaymentOrderPanel, "standingPaymentOrder");
    	cards.add(new StandingPaymentHistoryPanel(), "standingPaymentHistory");
    	cards.add(activeStandingOrdersPanel, "activeStandingOrders");
        cards.add(failedOrdersPanel, "failedOrders");

        cards.add(new EditStandingPaymentPanel(), "editStandingPayment");
    	cards.add(new EditStandingTransferPanel(), "editStandingTransfer");



    	add(cards); // add to frame content

    	miLogin.addActionListener(this);
    	miDashboard.addActionListener(this);
    	miAccounts.addActionListener(this);
    	miAbout.addActionListener(this);
    	miPayments.addActionListener(this);
    	miTransfers.addActionListener(this);
        miLogout.addActionListener(this);
        miExit.addActionListener(this);

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==miLogin)
			AppMediator.getCardLayout().show(AppMediator.getCards(), "login");
		else if (e.getSource()==miDashboard)
			AppMediator.getCardLayout().show(AppMediator.getCards(), "dashboard");
		else if (e.getSource()==miAccounts) {
            AccountsController.getInstance().setModel((Customer) AppMediator.getUser());
            AppMediator.getCardLayout().show(AppMediator.getCards(), "accounts");
        }
		else if (e.getSource()==miAbout)
			AppMediator.getCardLayout().show(AppMediator.getCards(), "about");
		else if (e.getSource()==miTransfers)
			AppMediator.getCardLayout().show(AppMediator.getCards(), "transfersPanel");
		else if (e.getSource()==miPayments)
			AppMediator.getCardLayout().show(AppMediator.getCards(), "payments");
        else if (e.getSource() == miLogout) {
            // Disable menu items
            disableUserMenu();
            // Return to login page
            AppMediator.getCardLayout().show(AppMediator.getCards(), "login");
        }
		else if (e.getSource()==miExit) {
            UserManager.getInstance().save();
            AccountManager.getInstance().save();
            StatementManager.getInstance().save();
            BillManager.getInstance().save();
            AdminRequestsManager.getInstance().save();
            StandingOrderManager.getInstance().save();
            System.exit(0);
        }
	}
}
