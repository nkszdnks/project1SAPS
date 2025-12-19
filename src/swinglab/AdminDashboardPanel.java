package swinglab;

import swinglab.Contollers.AdminRequestsController;
import swinglab.Contollers.AllStatementsController;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class AdminDashboardPanel extends JPanel implements ActionListener{

    JButton btnViewAccounts, btnAbout, btnLogout,btnViewStatements,btnRequests,btnSimulateTime;

    AdminDashboardPanel() {
        setLayout(new BorderLayout(10,10)); //(int hgap, int vgap)
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel title = new JLabel("Dashboard", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 3, 10, 10)); // (rows, cols, hgap, vgap)
        btnViewAccounts = new JButton("View Accounts");
        btnAbout = new JButton("About");
        btnLogout = new JButton("Logout");
        btnViewStatements = new JButton("View Statements");
        btnRequests = new JButton("Requests");
        btnSimulateTime = new JButton("Simulate Time");


        center.add(btnViewAccounts);
        center.add(btnAbout);
        center.add(btnLogout);
        center.add(btnViewStatements);
        center.add(btnRequests);
        center.add(btnSimulateTime);


        center.setPreferredSize(new Dimension(200, center.getPreferredSize().height));

        add(center, BorderLayout.CENTER);

        // events
        btnViewAccounts.addActionListener(this);
        btnAbout.addActionListener(this);
        btnLogout.addActionListener(this);
        btnViewStatements.addActionListener(this);
        btnRequests.addActionListener(this);
        btnSimulateTime.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnViewAccounts) {
//            AccountsController.getInstance().setModel((Customer) AppMediator.getUser());
//            AppMediator.getCardLayout().show(AppMediator.getCards(), "accounts");
        }
        else if (e.getSource()==btnAbout)
            AppMediator.getCardLayout().show(AppMediator.getCards(), "about");
        else if (e.getSource()==btnRequests) {
            AdminRequestsController.getInstance().loadPendingRequests();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "adminRequests");
        }
        else if (e.getSource()==btnSimulateTime) {
            AppMediator.getCardLayout().show(AppMediator.getCards(),"simulateTime");
        }
        else if (e.getSource()==btnViewStatements) {
            AllStatementsController.getInstance().loadStatements();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "allStatements");
        }
//        else if (e.getSource()==btnPayments)
//            AppMediator.getCardLayout().show(AppMediator.getCards(), "payments");
//        else if (e.getSource()==btnESO)
//            AppMediator.getCardLayout().show(AppMediator.getCards(), "activeStandingOrders");
        else if (e.getSource()==btnLogout) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "login");
            AppMediator.getBank().disableUserMenu();
            AppMediator.setUser(null);
        }
    }
}

