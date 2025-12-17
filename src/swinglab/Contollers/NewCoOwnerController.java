package swinglab.Contollers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Entities.Accounts.PersonalAccount;
import Entities.AdminRequests.NewCoOwnerRequest;
import Entities.Users.Customer;
import Entities.Users.IndividualPerson;
import Managers.AccountManager;
import Managers.AdminRequestsManager;
import Managers.UserManager;
import swinglab.AppMediator;
import swinglab.NewCoOwnerPanel;


public class NewCoOwnerController {

    private   NewCoOwnerPanel view;
    private  PersonalAccount  model;
    private static NewCoOwnerController instance;

    public void setModel(PersonalAccount model) {
        this.model = model;
    }
    public static NewCoOwnerController getInstance(){
        if(instance == null){
            return new NewCoOwnerController();
        }
        return instance;
    }

    public void setView(NewCoOwnerPanel view) {
        this.view = view;

        view.addSendListener(new SendRequestHandler());
        view.addCloseListener(e ->
                AppMediator.getCardLayout()
                        .show(AppMediator.getCards(), "coOwners")
        );
    }

    private NewCoOwnerController() {

    }


    /* ---------- Inner Action Handler ---------- */

    private class SendRequestHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            String name = view.getName();
            String afm  = view.getAfm();
            String email = view.getEmail();
            String phone = view.getPhone();

            // Basic validation
            if (name.isEmpty()) {
                view.showError("Co-Owner Name are required.");
                return;
            }
            IndividualPerson newCoOwner = UserManager.getInstance().findUserByFullName(name);
            if(newCoOwner==null ){
                view.showError("Co-Owner Name is invalid.");
                return;
            }
            if(!newCoOwner.getEmail().equals(email)||!newCoOwner.getPhoneNumber().equals(phone)||!newCoOwner.getAfm().equals(afm)){
                view.showError("Co-Owner personal details doesn't match.");
                return;
            }
            NewCoOwnerRequest newCoOwnerRequest = new NewCoOwnerRequest((Customer) AppMediator.getUser(),model,newCoOwner);
            AdminRequestsManager.getInstance().getAdminRequests().add(newCoOwnerRequest);

            // ---- Business call (stub) ----
            // AdminRequestManager.submitAddCoOwnerRequest(...)

            view.showInfo(
                    "Request submitted successfully.\n" +
                            "An administrator will review it."
            );
        }
    }
}
