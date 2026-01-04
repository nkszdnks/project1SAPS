package swinglab.View;

import Entities.Users.Customer;
import swinglab.Contollers.AccountsController;
import swinglab.Contollers.ActiveOrdersController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardPanel extends JPanel implements ActionListener {

    private JButton btnAccounts;
    private JButton btnTransfers;
    private JButton btnPayments;
    private JButton btnESO;
    private JButton btnCPD;
    private JButton btnAbout;
    private JButton btnLogout;

    public DashboardPanel() {

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        setBackground(Color.WHITE);

        // ===== Title =====
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        add(title, BorderLayout.NORTH);

        // ===== Center Panel =====
        JPanel center = new JPanel(new GridLayout(3, 3, 16, 16));
        center.setBackground(Color.WHITE);

        btnAccounts  = createDashboardButton("My Accounts");
        btnTransfers = createDashboardButton("Transfers");
        btnPayments  = createDashboardButton("Payments");
        btnESO       = createDashboardButton("Standing Orders");
        btnCPD       = createDashboardButton("Personal Details");
        btnAbout     = createDashboardButton("About");
        btnLogout    = createDashboardButton("Logout");

        // Logout emphasis
        btnLogout.setBackground(new Color(255, 235, 238));
        btnLogout.setForeground(new Color(180, 0, 0));
        btnLogout.setBorder(BorderFactory.createLineBorder(new Color(220, 50, 50)));

        center.add(btnAccounts);
        center.add(btnTransfers);
        center.add(btnPayments);
        center.add(btnESO);
        center.add(btnCPD);
        center.add(btnAbout);
        center.add(new JLabel()); // spacer
        center.add(btnLogout);
        center.add(new JLabel()); // spacer

        add(center, BorderLayout.CENTER);

        // ===== Events =====
        btnAccounts.addActionListener(this);
        btnTransfers.addActionListener(this);
        btnPayments.addActionListener(this);
        btnESO.addActionListener(this);
        btnCPD.addActionListener(this);
        btnAbout.addActionListener(this);
        btnLogout.addActionListener(this);
    }

    // ===== Button Factory =====
    private JButton createDashboardButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(245, 247, 250));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)
        ));
        return btn;
    }

    // ===== Actions =====
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAccounts) {
            AccountsController.getInstance()
                    .setModel((Customer) AppMediator.getUser());
            AppMediator.getCardLayout()
                    .show(AppMediator.getCards(), "accounts");
        }

        else if (e.getSource() == btnTransfers) {
            AppMediator.getCardLayout()
                    .show(AppMediator.getCards(), "transfersPanel");
        }

        else if (e.getSource() == btnPayments) {
            AppMediator.getCardLayout()
                    .show(AppMediator.getCards(), "payments");
        }

        else if (e.getSource() == btnESO) {
            ActiveOrdersController.getInstance()
                    .setModel((Customer) AppMediator.getUser());
            AppMediator.getCardLayout()
                    .show(AppMediator.getCards(), "activeStandingOrders");
        }

        else if (e.getSource() == btnCPD) {
            AppMediator.getCardLayout()
                    .show(AppMediator.getCards(), "changePersonalDetails");
        }

        else if (e.getSource() == btnAbout) {
            AppMediator.getCardLayout()
                    .show(AppMediator.getCards(), "about");
        }

        else if (e.getSource() == btnLogout) {
            AppMediator.getCardLayout()
                    .show(AppMediator.getCards(), "login");
            AppMediator.getBank().disableUserMenu();
            AppMediator.setUser(null);
        }
    }
}
