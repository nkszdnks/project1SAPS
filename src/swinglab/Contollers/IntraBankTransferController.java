package swinglab.Contollers;

import Entities.Accounts.BankAcount;
import Entities.Transactions.Rails.TransferRail;
import Entities.Transactions.Requests.TransferRequest;
import Managers.AccountManager;
import swinglab.AppMediator;
import swinglab.InterBankTransferPanel;
import swinglab.IntraBankTransferPanel;
import swinglab.TransferTypeHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Entities.Transactions.Requests.TransferRequest.Rail.*;

public class IntraBankTransferController implements ActionListener {
    private static IntraBankTransferController instance;
    private  IntraBankTransferPanel viewIntra;
    private  InterBankTransferPanel viewInter;

    private IntraBankTransferController() {
    }
    public static IntraBankTransferController getInstance(){
        if (instance == null){
            instance = new IntraBankTransferController();
            return instance;
        }
        return instance;
    }

    public void setViewIntra(IntraBankTransferPanel viewIntra) {
        this.viewIntra = viewIntra;
        viewIntra.btnFinish.addActionListener(this);
        viewIntra.btnClose.addActionListener(this);
    }

    public void setViewInter(InterBankTransferPanel viewInter) {
        this.viewInter = viewInter;
        viewInter.addCloseListener(this);
        viewInter.addFinishListener(this);
    }

    public void setIbanFields(){
        JComboBoxController.getInstance().fillAccountsJComboBox(viewIntra);
        JComboBoxController.getInstance().fillAccountsJComboBox(viewInter);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Finish")) {
            handleFinish();
        }
        if (e.getActionCommand().equals("Finish Inter-Bank Transfer")) {
            handleIntraFinish();
        }



        if (e.getActionCommand().equals("Close")) {
            AppMediator.getCardLayout().show(
                    AppMediator.getCards(),
                    "transfersPanel"
            );
        }
    }

    private void handleIntraFinish() {
        try {
            String from = viewInter.getFromIban();
            String to = viewInter.getCreditorIban().isEmpty()?viewInter.getBeneficiaryAccount():viewInter.getCreditorIban();
            String reason = viewInter.getReason();
            double amt = viewInter.getAmount();

            if (from.isEmpty() || to.isEmpty()) {
                JOptionPane.showMessageDialog(viewInter,
                        "IBAN fields cannot be empty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (amt <= 0) {
                JOptionPane.showMessageDialog(viewInter,
                        "Amount must be positive.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            var svc = new TransferRail();
            TransferRequest.Rail rail = TransferRequest.Rail.valueOf(viewInter.getTransferType());
            var okLocal = new TransferRequest(from,to,amt, rail,AppMediator.getUser().getUserId(), reason);
            boolean isSuccesfull = svc.execute(okLocal,viewInter.getTransferDetails());
            if(!isSuccesfull){
                throw new Exception(svc.getMessage());
            }
            JOptionPane.showMessageDialog(viewInter,
                    svc.getMessage());

        } catch (NullPointerException nullPointerException) {
            JOptionPane.showMessageDialog(viewInter,
                    "Please fill all fields. "+ nullPointerException.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(viewInter,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    private void handleFinish() {
        try {
            String from = String.valueOf(viewIntra.fromIbans.getSelectedItem());
            String to = viewIntra.toIban.getText().trim();
            String reason = viewIntra.reason.getText().trim();
            double amt = Double.parseDouble(viewIntra.amount.getText().trim());

            if (from.isEmpty() || to.isEmpty()) {
                JOptionPane.showMessageDialog(viewIntra,
                        "IBAN fields cannot be empty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (amt <= 0) {
                JOptionPane.showMessageDialog(viewIntra,
                        "Amount must be positive.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            BankAcount targetAccount = AccountManager.getInstance().findAccountByIBAN(to);
            if(targetAccount == null){
                JOptionPane.showMessageDialog(viewIntra,
                        "Target account does not exists!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            var svc = new TransferRail();

            var okLocal = new TransferRequest(from,to,amt, LOCAL,AppMediator.getUser().getUserId(), reason);
            boolean isSuccesfull = svc.execute(okLocal,null);
            if(!isSuccesfull){
                throw new Exception(svc.getMessage());
            }
            JOptionPane.showMessageDialog(viewIntra,
                    svc.getMessage());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(viewIntra,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
