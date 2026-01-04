package swinglab.View;

import Entities.Users.Customer;
import swinglab.Contollers.AccountsController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BusinessDashboardPanel extends JPanel implements ActionListener {

    JButton btnAccounts, btnAbout, btnLogout, btnTransfers, btnPayments,
            btnCPD, btnESO, btnPayBills, btnSPO, btnSPH, btnIssueBill, btnCompanyReceipts;

    public BusinessDashboardPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Business Dashboard", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3, 4, 10, 10)); // 3 γραμμές, 4 στήλες

        btnAccounts = new JButton("My Accounts");
        btnTransfers = new JButton("Transfers");
        btnPayments = new JButton("Payments");
        btnPayBills = new JButton("Pay Bills");
        btnSPO = new JButton("Standing Payment Order");
        btnSPH = new JButton("Standing Payment History");
        btnESO = new JButton("Edit Standing Orders");
        btnCPD = new JButton("Change Personal Details");
        btnIssueBill = new JButton("Issue Bill");
        btnCompanyReceipts = new JButton("Company Receipts");
        btnAbout = new JButton("About");
        btnLogout = new JButton("Logout");

        // Προσθήκη στο grid
        center.add(btnAccounts);
        center.add(btnTransfers);
        center.add(btnPayments);
        center.add(btnIssueBill);
        center.add(btnCompanyReceipts);
        center.add(btnPayBills);
        center.add(btnSPO);
        center.add(btnSPH);
        center.add(btnESO);
        center.add(btnCPD);
        center.add(btnAbout);
        center.add(btnLogout);

        add(center, BorderLayout.CENTER);

        // Listeners
        btnAccounts.addActionListener(this);
        btnTransfers.addActionListener(this);
        btnPayments.addActionListener(this);
        btnPayBills.addActionListener(this);
        btnSPO.addActionListener(this);
        btnSPH.addActionListener(this);
        btnESO.addActionListener(this);
        btnCPD.addActionListener(this);
        btnAbout.addActionListener(this);
        btnLogout.addActionListener(this);
        btnIssueBill.addActionListener(this);
        btnCompanyReceipts.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnAccounts) {
            AccountsController.getInstance().setModel((Customer) AppMediator.getUser());
            AppMediator.getCardLayout().show(AppMediator.getCards(), "accounts");
        } else if (src == btnTransfers) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "transfersPanel");
        } else if (src == btnPayments) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "payments");
        } else if (src == btnPayBills) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "payBills");
        } else if (src == btnSPO) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "standingPaymentOrder");
        } else if (src == btnSPH) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "standingPaymentHistory");
        } else if (src == btnESO) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "activeStandingOrders");
        } else if (src == btnCPD) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "changePersonalDetails");
        } else if (src == btnAbout) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "about");
        } else if (src == btnIssueBill) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "issueBill");
        } else if (src == btnCompanyReceipts) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "companyReceipts");
        } else if (src == btnLogout) {
            AppMediator.getCardLayout().show(AppMediator.getCards(), "login");
            AppMediator.getBank().disableUserMenu();
            AppMediator.setUser(null);
        }
    }
}