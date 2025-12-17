package swinglab.Contollers;


import Entities.Accounts.PersonalAccount;
import Entities.Users.IndividualPerson;
import Managers.UserManager;
import swinglab.AppMediator;
import swinglab.CoOwnersPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CoOwnersController extends WindowAdapter implements ActionListener {
    private static CoOwnersController instance;
    private  CoOwnersPanel view;
    private PersonalAccount model;


    public static CoOwnersController getInstance() {
        if (instance == null) {
            instance = new CoOwnersController();
            return instance;
        }
        return instance;
    }

    public void setModel(PersonalAccount model) {
        this.model = model;
        loadCoOwners();
    }

    public void setView(CoOwnersPanel view) {
        this.view = view;

        // Attach listeners
        view.addNew.addActionListener(this);
        view.removeSelected.addActionListener(this);
        view.clearSel.addActionListener(this);
        view.closePan.addActionListener(this);
    }

    private void loadCoOwners() {
        view.clearTable();

        for (IndividualPerson p : model.getSecondaryOwners()) {
            view.addCoOwnerRow(
                    p.getFirstName()+p.getLastName(),
                    p.getAfm()
            );
        }
    }

    private CoOwnersController() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {

            case "Add new":
                NewCoOwnerController.getInstance().setModel(model);
                AppMediator.getCardLayout().show(AppMediator.getCards(), "newCoOwner");
                break;

            case "RemoveSelected":
                removeSelectedRow();
                break;

            case "Clear Selection":
                view.clearSelection();
                break;

            case "Close":
                AppMediator.getCardLayout().show(AppMediator.getCards(), "dashboard");
                break;

            default:
                System.out.println("Unhandled: " + cmd);
        }
    }

    private void removeSelectedRow() {
        int row = view.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view,
                    "Please select a co-owner first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ✔ Read name from the table BEFORE deleting
        String name = view.getNameAtRow(row);
        IndividualPerson p = UserManager.getInstance().findUserByFullName(name);
        model.getSecondaryOwners().remove(p);
        // or AccountManager.getInstance().removeCoOwner(name);

        // ✔ Remove from the table view
        view.removeRow(row);

        JOptionPane.showMessageDialog(view,
                "Co-owner \"" + name + "\" removed.",
                "Removed",
                JOptionPane.INFORMATION_MESSAGE);
    }


    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Exit Application");
        System.exit(0);
    }
}

