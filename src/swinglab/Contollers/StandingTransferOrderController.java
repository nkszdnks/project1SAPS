package swinglab.Contollers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

import Entities.StandingOrders.OrderStatus;
import Entities.StandingOrders.PaymentOrder;
import Entities.Transactions.Requests.TransferRequest;
import Entities.Users.Bills;
import Entities.Users.Customer;
import Entities.StandingOrders.TransferOrder;
import Managers.AccountManager;
import Managers.BillManager;
import Managers.StandingOrderManager;
import Managers.UserManager;
import swinglab.View.AppMediator;
import swinglab.View.StandingPaymentOrderPanel;
import swinglab.View.StandingTransferOrderPanel;

public class StandingTransferOrderController {

    private StandingTransferOrderPanel viewTransfer;
    private StandingPaymentOrderPanel viewPayment;
    private static StandingTransferOrderController instance;
    private Customer model;

    private StandingTransferOrderController() {}

    public static StandingTransferOrderController getInstance() {
        if (instance == null) {
            instance = new StandingTransferOrderController();
        }
        return instance;
    }

    public void setModel(Customer model) {
        this.model = model;
        JComboBoxController.getInstance().fillAccountsJComboBox(viewTransfer);
        JComboBoxController.getInstance().fillAccountsJComboBox(viewPayment);
        viewPayment.setBusinesses(UserManager.getInstance().getBusinessesNames());
    }

    public void setViewTransfer(StandingTransferOrderPanel view) {
        this.viewTransfer = view;

        view.addFinishListener(new FinishHandler());
        view.addCloseListener(new CloseHandler());
    }
    public void setViewPayment(StandingPaymentOrderPanel view) {
        this.viewPayment = view;

        view.addFinishListener(new FinishPaymentHandler());
        view.addCloseListener(new ClosePaymentHandler());
    }

    /* ================== Handlers ================== */

    private class FinishHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            StandingTransferOrderPanel view = viewTransfer;

            /* -------- Basic validation -------- */

            String fromIban = view.getFromIban().trim();
            String toIban = view.getToIban().trim();
            String amountStr = view.getAmount().trim();
            String title = view.getTitle().trim();

            if (fromIban.isEmpty() || toIban.isEmpty() || amountStr.isEmpty()) {
                view.showError("All fields are required.");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException ex) {
                view.showError("Amount must be a number.");
                return;
            }

            if (amount <= 0) {
                view.showError("Amount must be positive.");
                return;
            }

            /* -------- Dates -------- */

            LocalDate startDate = view.getStartDate();
            LocalDate endDate = view.getEndDate();

            if (endDate.isBefore(startDate)) {
                view.showError("End date cannot be before start date.");
                return;
            }

            int executionDay = view.getExecutionDay();
            int frequencyMonths = view.getFrequencyMonths();

            /* -------- Create order -------- */
            if(AccountManager.getInstance().findAccountByIBAN(toIban) == null){
                view.showError("Destination Iban not found.");
                return;
            }

            TransferOrder order =
                    new TransferOrder(
                            ((Customer) AppMediator.getUser()).getUserId(),
                            LocalDateTime.now().getSecond()+LocalDateTime.now().getMinute()+((Customer) AppMediator.getUser()).getUserId(),
                            title,
                            view.getDescription(),
                            executionDay,
                            frequencyMonths,
                            startDate,
                            endDate,
                            Double.parseDouble(view.getMaxAmount()),
                            OrderStatus.ACTIVE,
                            toIban,
                            amount,
                            view.getFromIban(),
                            0,
                            0.1,
                            TransferRequest.Rail.LOCAL
                    );

            StandingOrderManager
                    .getInstance()
                    .createOrder(order);

            view.showInfo(
                    "Standing transfer order created successfully.\n" +
                            "The system will execute it automatically."
            );

            AppMediator.getCardLayout()
                    .show(AppMediator.getCards(), "transfersPanel");
        }
    }

    private class FinishPaymentHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            StandingPaymentOrderPanel view = viewPayment;

            /* -------- Basic validation -------- */

            String fromIban = view.getFromIban().trim();
            String rfCode = view.getRfCode().trim();
            String title = view.getTitle().trim();
            String maxAmount = view.getMaxAmount().trim();

            if (fromIban.isEmpty() || rfCode.isEmpty() ) {
                view.showError("All fields are required.");
                return;
            }
            if (!rfCode.startsWith("RF") && !(rfCode.length() == 10) ) {
                view.showError("Invalid RF code!");
                return;
            }
            Bills bill = BillManager.getInstance().findBill(rfCode);
            if ( bill!= null && !bill.getCustomer().equals((Customer) AppMediator.getUser())) {
                view.showError("The bill you are trying to pay belongs to another user!");
                return;
            }

            if ( bill!= null && !bill.getIssuer().getBusinessName().equals(view.getSelectedBusiness())) {
                view.showError("RF code and business do not match!");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(maxAmount);
            } catch (NumberFormatException ex) {
                view.showError("Amount must be a number.");
                return;
            }

            if (amount <= 0) {
                view.showError("Max Amount must be positive.");
                return;
            }

            /* -------- Dates -------- */

            LocalDate startDate = view.getStartDate();
            LocalDate endDate = view.getEndDate();

            if (endDate.isBefore(startDate)) {
                view.showError("End date cannot be before start date.");
                return;
            }





            PaymentOrder order =
                    new PaymentOrder(
                            ((Customer) AppMediator.getUser()).getUserId(),
                            "TestID"+ LocalDateTime.now().getSecond()+LocalDateTime.now().getMinute(),
                            title,
                            view.getDescription(),
                            startDate,
                            endDate,
                            Double.parseDouble(view.getMaxAmount()),
                            OrderStatus.ACTIVE,
                            rfCode,
                            fromIban,
                            0,
                            0.1
                    );

            StandingOrderManager
                    .getInstance()
                    .createOrder(order);

            view.showInfo(
                    "Standing payment order created successfully.\n" +
                            "The system will execute it automatically."
            );

            AppMediator.getCardLayout()
                    .show(AppMediator.getCards(), "payments");
        }
    }

    private class CloseHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AppMediator.getCardLayout()
                    .show(AppMediator.getCards(), "transfersPanel");
        }
    }
    private class ClosePaymentHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AppMediator.getCardLayout()
                    .show(AppMediator.getCards(), "payments");
        }
    }


}
