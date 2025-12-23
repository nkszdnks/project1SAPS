package swinglab;

import Entities.Accounts.BankAcount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class InterBankTransferPanel extends JPanel implements hasIbanField {

    /* -------- Common -------- */
    private final JComboBox<String> transferType =
            new JComboBox<>(new String[]{"SEPA", "SWIFT"});

    private final JComboBox<String> fromIbans = new JComboBox<>();
    private final JTextField amount = new JTextField(10);
    private final JTextField reason = new JTextField(50);
    private final JTextField charges = new JTextField(5); // SHA | OUR

    private final JLabel lblFromIban = new JLabel("From IBAN:");
    private final JLabel lblAmount = new JLabel("Amount:");
    private final JLabel lblReason = new JLabel("Reason:");
    private final JLabel lblCharges = new JLabel("Charges (SHA / OUR):");
    private final JLabel lblTransferType = new JLabel("Transfer type:");

    /* -------- SEPA -------- */
    private final JTextField creditorName = new JTextField(22);
    private final JTextField creditorIban = new JTextField(22);
    private final JTextField creditorBankName = new JTextField(22);
    private final JTextField creditorBic = new JTextField(11);
    private final JTextField requestedDate = new JTextField(10); // YYYY-MM-DD

    private final JLabel lblCreditorName = new JLabel("Creditor name:");
    private final JLabel lblCreditorIban = new JLabel("Creditor IBAN:");
    private final JLabel lblCreditorBankName = new JLabel("Creditor bank name:");
    private final JLabel lblCreditorBic = new JLabel("Creditor BIC:");

    /* -------- SWIFT -------- */
    private final JTextField currency = new JTextField(5);
    private final JTextField beneficiaryName = new JTextField(22);
    private final JTextField beneficiaryAddress = new JTextField(22);
    private final JTextField beneficiaryAccount = new JTextField(22);
    private final JTextField bankName = new JTextField(22);
    private final JTextField swiftCode = new JTextField(11);
    private final JTextField country = new JTextField(5);

    private final JLabel lblCurrency = new JLabel("Currency:");
    private final JLabel lblBeneficiaryName = new JLabel("Beneficiary name:");
    private final JLabel lblBeneficiaryAddress = new JLabel("Beneficiary address:");
    private final JLabel lblBeneficiaryAccount = new JLabel("Beneficiary account:");
    private final JLabel lblBankName = new JLabel("Bank name:");
    private final JLabel lblSwiftCode = new JLabel("SWIFT code:");
    private final JLabel lblCountry = new JLabel("Country:");

    /* -------- Buttons -------- */
    private final JButton btnFinish = new JButton("Finish Inter-Bank Transfer");
    private final JButton btnClose = new JButton("Close");

    private final GridBagConstraints c = new GridBagConstraints();

    public InterBankTransferPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Transfer type
        addRow(row++, lblTransferType, transferType);

        // Common fields
        addRow(row++, lblFromIban, fromIbans);
        addRow(row++, lblAmount, amount);
        addRow(row++, lblCharges, charges);
        addRow(row++, lblReason, reason);

        // SEPA fields
        addRow(row++, lblCreditorName, creditorName);
        addRow(row++, lblCreditorIban, creditorIban);
        addRow(row++, lblCreditorBankName, creditorBankName);
        addRow(row++, lblCreditorBic, creditorBic);

        // SWIFT fields
        addRow(row++, lblCurrency, currency);
        addRow(row++, lblBeneficiaryName, beneficiaryName);
        addRow(row++, lblBeneficiaryAddress, beneficiaryAddress);
        addRow(row++, lblBeneficiaryAccount, beneficiaryAccount);
        addRow(row++, lblBankName, bankName);
        addRow(row++, lblSwiftCode, swiftCode);
        addRow(row++, lblCountry, country);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnClose);
        buttons.add(btnFinish);

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        add(buttons, c);

        // Behavior
        transferType.addActionListener(e -> updateVisibility());
        updateVisibility(); // default to SEPA
    }

    /* ================= Layout Helper ================= */
    private void addRow(int y, JLabel label, JComponent field) {
        c.gridx = 0;
        c.gridy = y;
        c.gridwidth = 1;
        add(label, c);

        c.gridx = 1;
        add(field, c);
    }

    /* ================= Visibility ================= */
    private void updateVisibility() {
        boolean isSepa = "SEPA".equals(getTransferType());

        // SEPA
        creditorName.setVisible(isSepa);
        creditorIban.setVisible(isSepa);
        creditorBankName.setVisible(isSepa);
        creditorBic.setVisible(isSepa);
        requestedDate.setVisible(isSepa);

        lblCreditorName.setVisible(isSepa);
        lblCreditorIban.setVisible(isSepa);
        lblCreditorBankName.setVisible(isSepa);
        lblCreditorBic.setVisible(isSepa);

        // SWIFT
        currency.setVisible(!isSepa);
        beneficiaryName.setVisible(!isSepa);
        beneficiaryAddress.setVisible(!isSepa);
        beneficiaryAccount.setVisible(!isSepa);
        bankName.setVisible(!isSepa);
        swiftCode.setVisible(!isSepa);
        country.setVisible(!isSepa);

        lblCurrency.setVisible(!isSepa);
        lblBeneficiaryName.setVisible(!isSepa);
        lblBeneficiaryAddress.setVisible(!isSepa);
        lblBeneficiaryAccount.setVisible(!isSepa);
        lblBankName.setVisible(!isSepa);
        lblSwiftCode.setVisible(!isSepa);
        lblCountry.setVisible(!isSepa);

        revalidate();
        repaint();
    }

    /* ================= Getters (Controller API) ================= */
    public String getTransferType() { return (String) transferType.getSelectedItem(); }
    public String getFromIban() { return String.valueOf(fromIbans.getSelectedItem()); }
    public double getAmount() { return Double.parseDouble(amount.getText().trim()); }
    public String getCharges() { return charges.getText().trim(); }
    public String getReason() { return reason.getText().trim(); }

    /* ---- SEPA ---- */
    public String getCreditorName() { return creditorName.getText().trim(); }
    public String getCreditorIban() { return creditorIban.getText().trim(); }
    public String getCreditorBankName() { return creditorBankName.getText().trim(); }
    public String getCreditorBic() { return creditorBic.getText().trim(); }

    /* ---- SWIFT ---- */
    public String getCurrency() { return currency.getText().trim(); }
    public String getBeneficiaryName() { return beneficiaryName.getText().trim(); }
    public String getBeneficiaryAddress() { return beneficiaryAddress.getText().trim(); }
    public String getBeneficiaryAccount() { return beneficiaryAccount.getText().trim(); }
    public String getBankName() { return bankName.getText().trim(); }
    public String getSwiftCode() { return swiftCode.getText().trim(); }
    public String getCountry() { return country.getText().trim(); }

    /* ---- HashMap getters for controller convenience ---- */
    public HashMap<String, String> getTransferDetails() {
        HashMap<String, String> map = new HashMap<>();
        map.put("fromIban", getFromIban());
        map.put("amount", String.valueOf(getAmount()));
        map.put("charges", getCharges());
        map.put("reason", getReason());

        if ("SEPA".equals(getTransferType())) {
            map.put("creditorName", getCreditorName());
            map.put("creditorIban", getCreditorIban());
            map.put("creditorBankName", getCreditorBankName());
            map.put("creditorBic", getCreditorBic());
        } else {
            map.put("currency", getCurrency());
            map.put("beneficiaryName", getBeneficiaryName());
            map.put("beneficiaryAddress", getBeneficiaryAddress());
            map.put("beneficiaryAccount", getBeneficiaryAccount());
            map.put("bankName", getBankName());
            map.put("swiftCode", getSwiftCode());
            map.put("country", getCountry());
        }

        return map;
    }
    @Override
    public void setIbans(ArrayList<BankAcount> accounts) {
        fromIbans.removeAllItems();
        for (BankAcount b : accounts)
            fromIbans.addItem(b.getIBAN());
    }

    /* ---- Buttons ---- */
    public void addFinishListener(ActionListener l) { btnFinish.addActionListener(l); }
    public void addCloseListener(ActionListener l) { btnClose.addActionListener(l); }
}
