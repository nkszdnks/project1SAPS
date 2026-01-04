package swinglab.Contollers;

import Commands.ScheduledTransferCommand;
import Entities.Transactions.Rails.TransferRail;
import Entities.Transactions.Requests.TransferRequest;
import Managers.StandingOrderManager;
import swinglab.View.AppMediator;
import swinglab.View.InterBankTransferPanel;
import swinglab.View.IntraBankTransferPanel;

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


            TransferRequest.Rail rail = TransferRequest.Rail.valueOf(viewInter.getTransferType());
            var okLocal = new TransferRequest(from,to,amt, rail,AppMediator.getUser().getUserId(), reason);

            if(viewInter.scheduleChoice.getSelectedIndex() == 1){
                if (viewInter.getSelectedDate().isBefore(AppMediator.getToday())) {
                    JOptionPane.showMessageDialog(viewInter,
                            "You can't schedule in past dates!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                StandingOrderManager.getInstance().getScheduledTransfers().add(new ScheduledTransferCommand(okLocal,viewInter.getSelectedDate(),viewInter.getTransferDetails()));
                JOptionPane.showMessageDialog(viewInter,
                        rail + " Transfer Scheduled Successfully! It will executed automatically at:"+"\n"+String.valueOf(viewInter.getSelectedDate()));
                return;
            }

            var svc = new TransferRail();
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



            var okLocal = new TransferRequest(from,to,amt, LOCAL,AppMediator.getUser().getUserId(), reason);
            if(viewIntra.scheduleChoice.getSelectedIndex() == 1){
                if (viewIntra.getSelectedDate().isBefore(AppMediator.getToday())) {
                    JOptionPane.showMessageDialog(viewIntra,
                            "You can't schedule in past dates!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                StandingOrderManager.getInstance().getScheduledTransfers().add(new ScheduledTransferCommand(okLocal,viewIntra.getSelectedDate(),null));
                JOptionPane.showMessageDialog(viewIntra,
                        "Transfer Scheduled Successfully! It will executed automatically at:"+"\n"+String.valueOf(viewIntra.getSelectedDate()));
                return;
            }
            var svc = new TransferRail();
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
