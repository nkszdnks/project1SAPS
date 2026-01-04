package swinglab.View;

import swinglab.Contollers.*;

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

public class AdminDashboardPanel extends JPanel implements ActionListener{

    JButton btnViewAllUsers,btnViewAccounts,getBtnViewBills, btnViewStandingOrders, btnLogout,btnViewStatements,btnRequests,btnSimulateTime;

    public AdminDashboardPanel() {
        setLayout(new BorderLayout(10,10)); //(int hgap, int vgap)
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel title = new JLabel("Dashboard", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 3, 10, 10)); // (rows, cols, hgap, vgap)
        btnViewAccounts = new JButton("View Accounts");
        btnViewAllUsers = new JButton("View All Users");
        btnViewStandingOrders = new JButton("View Standing Orders");
        getBtnViewBills = new JButton("View All Bills");
        btnViewStatements = new JButton("View Statements");
        btnRequests = new JButton("Requests");
        btnSimulateTime = new JButton("Simulate Time");


        center.add(btnViewAccounts);
        center.add(btnViewAllUsers);
        center.add(btnViewStandingOrders);
        center.add(getBtnViewBills);
        center.add(btnViewStatements);
        center.add(btnRequests);
        center.add(btnSimulateTime);


        center.setPreferredSize(new Dimension(200, center.getPreferredSize().height));

        add(center, BorderLayout.CENTER);

        // events
        btnViewAllUsers.addActionListener(this);
        btnViewAccounts.addActionListener(this);
        btnViewStandingOrders.addActionListener(this);
        getBtnViewBills.addActionListener(this);
        btnViewStatements.addActionListener(this);
        btnRequests.addActionListener(this);
        btnSimulateTime.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnViewAccounts) {
            AccountsController.getInstance().setModel(null);
            AccountsController.getInstance().loadAllAccounts();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "accounts");
        }
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
        else if (e.getSource()==btnViewAllUsers) {
            UsersController.getInstance().loadAllUsers();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "allUsers");
        }
      else if (e.getSource()==btnViewStandingOrders){
          ActiveOrdersController.getInstance().setModel(null);
            ActiveOrdersController.getInstance().loadAllOrders();
           AppMediator.getCardLayout().show(AppMediator.getCards(), "activeStandingOrders");
      }
        else if (e.getSource()==getBtnViewBills){
            AllStatementsController.getInstance().loadBills();
            AppMediator.getCardLayout().show(AppMediator.getCards(), "allBills");
        }

//        else if (e.getSource()==btnESO)
//            AppMediator.getCardLayout().show(AppMediator.getCards(), "activeStandingOrders");
        else if (e.getSource()==btnLogout) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "login");
            AppMediator.getBank().disableUserMenu();
            AppMediator.setUser(null);
        }
    }
}

