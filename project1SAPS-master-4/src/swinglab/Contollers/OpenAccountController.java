package swinglab.Contollers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Entities.AdminRequests.NewAccountRequest;
import Entities.Users.Customer;
import Entities.Users.IndividualPerson;
import Managers.AdminRequestsManager;
import Managers.UserManager;
import swinglab.View.AppMediator;
import swinglab.View.OpenAcountPanel;

public class OpenAccountController {

    private OpenAcountPanel view;
    private static OpenAccountController instance;

    private OpenAccountController() {}

    public static OpenAccountController getInstance() {
        if (instance == null) {
            instance = new OpenAccountController();
        }
        return instance;
    }

    public void setView(OpenAcountPanel view) {
        this.view = view;

        view.addFinishListener(new FinishHandler());
        view.addClearListener(e -> view.clearForm());
        view.addAddCoOwnerListener(new AddCoOwnerHandler());
    }

    /* ================== Handlers ================== */

    private class FinishHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String depositStr = view.getInitialDeposit().trim();
            if (depositStr.isEmpty()) {
                view.showError("Initial deposit is required.");
                return;
            }

            double deposit;
            try {
                deposit = Double.parseDouble(depositStr);
            } catch (NumberFormatException ex) {
                view.showError("Initial deposit must be a number.");
                return;
            }

            if (deposit < 0) {
                view.showError("Initial deposit cannot be negative.");
                return;
            }

            ArrayList<IndividualPerson> coOwners = new ArrayList<>();

            for (String afm : view.getCoOwnerAfms()) {
                IndividualPerson p = UserManager.getInstance().findUserByAfm(afm);
                if (p == null) {
                    view.showError("Invalid co-owner AFM: " + afm);
                    return;
                }
                coOwners.add(p);
            }

            NewAccountRequest request =
                    new NewAccountRequest(
                            (Customer) AppMediator.getUser(),
                            deposit,
                            coOwners
                    );

            AdminRequestsManager
                    .getInstance()
                    .getAdminRequests()
                    .add(request);

            view.showInfo(
                    "Account opening request submitted.\n" +
                            "An administrator will review it."
            );
        }
    }

    private class AddCoOwnerHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.addNextCoOwnerField();
        }
    }
}
