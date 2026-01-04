package swinglab.Contollers;

import Entities.AdminRequests.AdminRequest;
import Entities.Users.Admin;
import Managers.AdminRequestsManager;
import swinglab.View.AdminRequestsPanel;
import swinglab.View.AppMediator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminRequestsController implements ActionListener {

    private static AdminRequestsController instance;

    private AdminRequestsPanel view;
    private Admin model;

    private AdminRequestsController() {
        // private constructor (singleton)
    }

    public static AdminRequestsController getInstance() {
        if (instance == null) {
            instance = new AdminRequestsController();
        }
        return instance;
    }

    /* =========================
       Wiring MVC
       ========================= */

    public void setView(AdminRequestsPanel view) {
        this.view = view;


        view.detailsBtn.addActionListener(this);
        view.closeBtn.addActionListener(this);
        loadPendingRequests();
    }

    public void setModel(Admin admin) {
        this.model = admin;
        loadPendingRequests();
    }

    /* =========================
       Load data
       ========================= */

    public void loadPendingRequests() {
        view.clearTable();

        for (AdminRequest req :
                AdminRequestsManager.getInstance().getAdminRequests()) {

            view.addRequestRow(
                    req.getRequestID(),
                    req.getRequestType(),
                    req.getCustomer().getFullName(),// if exists
                    req.getRequestStatus().name()
            );
        }
    }

    /* =========================
       Actions
       ========================= */

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        switch (cmd) {



            case "View Details":
                view.showSelectedRowDetails();
                break;

            case "Close":
                AppMediator.getCardLayout()
                        .show(AppMediator.getCards(), "adminDashboard");
                break;

            default:
                System.out.println("Unhandled action: " + cmd);
        }
    }

    /* =========================
       Helpers
       ========================= */

//    private void approveSelected() {
//        AdminRequest req = view.getSelectedRequest();
//        if (req == null) return;
//
//        req.setAdmin(model);
//        req.setRequestStatus(RequestStatus.APPROVED);
//
//        AdminRequestManager.getInstance().process(req);
//
//        loadPendingRequests();
//    }

//    private void rejectSelected() {
//        AdminRequest req = view.getSelectedRequest();
//        if (req == null) return;
//
//        req.setAdmin(model);
//        req.setRequestStatus(RequestStatus.REJECTED);
//
//        AdminRequestManager.getInstance().process(req);
//
//        loadPendingRequests();
//    }
}
