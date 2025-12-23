package swinglab;

import Entities.Users.Customer;
import swinglab.Contollers.AccountsController;
import swinglab.Contollers.ActiveOrdersController;

import java.awt.BorderLayout;
import javax.swing.*;
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

public class DashboardPanel extends JPanel implements ActionListener{
	
	JButton btnAccounts, btnAbout, btnLogout,btnTransfers,btnPayments,btnCPD,btnESO;
    
    public DashboardPanel() {
        setLayout(new BorderLayout(10,10)); //(int hgap, int vgap)
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel title = new JLabel("Dashboard", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 3, 10, 10)); // (rows, cols, hgap, vgap)
        btnAccounts = new JButton("My Accounts");
        btnAbout = new JButton("About");
        btnLogout = new JButton("Logout");
        btnTransfers = new JButton("Transfers");
        btnPayments = new JButton("Payments");
        btnCPD = new JButton("Change Personal Details");
        btnESO = new JButton("Edit Standing Orders");


        center.add(btnAccounts);
        center.add(btnAbout);
        center.add(btnLogout);
        center.add(btnTransfers);
        center.add(btnPayments);
        center.add(btnCPD);
        center.add(btnESO);
        
        center.setPreferredSize(new Dimension(200, center.getPreferredSize().height));
        
        add(center, BorderLayout.CENTER);

        // events
        btnAccounts.addActionListener(this);
        btnAbout.addActionListener(this);
        btnLogout.addActionListener(this);
        btnTransfers.addActionListener(this);

        btnPayments.addActionListener(this);
        btnCPD.addActionListener(this);
        btnESO.addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btnAccounts) {
            AccountsController.getInstance().setModel((Customer) AppMediator.getUser());
            AppMediator.getCardLayout().show(AppMediator.getCards(), "accounts");
        }
		else if (e.getSource()==btnAbout)
			AppMediator.getCardLayout().show(AppMediator.getCards(), "about");
		else if (e.getSource()==btnTransfers)
			AppMediator.getCardLayout().show(AppMediator.getCards(), "transfersPanel");
		else if (e.getSource()==btnCPD)
			AppMediator.getCardLayout().show(AppMediator.getCards(), "changePersonalDetails");
		else if (e.getSource()==btnPayments)
			AppMediator.getCardLayout().show(AppMediator.getCards(), "payments");
		else if (e.getSource()==btnESO) {
            ActiveOrdersController.getInstance().setModel((Customer) AppMediator.getUser());
            AppMediator.getCardLayout().show(AppMediator.getCards(), "activeStandingOrders");
        }
		else if (e.getSource()==btnLogout) {
			AppMediator.getCardLayout().show(AppMediator.getCards(), "login");
			AppMediator.getBank().disableUserMenu();
			AppMediator.setUser(null);
		}
	}
}

