package swinglab.Contollers;

import Entities.Users.User;
import Managers.UserManager;
import swinglab.View.AppMediator;
import swinglab.View.UsersPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UsersController extends WindowAdapter implements ActionListener {

    private static UsersController instance;

    private UsersPanel view;

    public static UsersController getInstance() {
        if (instance == null) {
            instance = new UsersController();
        }
        return instance;
    }

    private UsersController() {
        // singleton
    }

    /* ================== SETTERS ================== */

    public void setView(UsersPanel view) {
        this.view = view;

        view.details.addActionListener(this);
        view.clearSel.addActionListener(this);
        view.closePan.addActionListener(this);
    }



    /* ================== LOAD DATA ================== */

    public void loadAllUsers() {

        view.clearTable();

        for (User user : UserManager.getInstance().getUsers()) {
            view.addUserRow(user);
        }
    }

    /* ================== ACTIONS ================== */

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {

            case "View Details":
                view.showSelectedUserDetails();
                break;

            case "Clear Selection":
                view.clearSelection();
                break;

            case "Close":
                AppMediator.getCardLayout()
                        .show(AppMediator.getCards(), "adminDashboard");
                break;

            default:
                System.out.println("Unhandled action: " + cmd);
        }
    }

    /* ================== WINDOW ================== */

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Window closed.");
        System.exit(0);
    }
}
