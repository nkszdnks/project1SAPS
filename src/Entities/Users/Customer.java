package Entities.Users;

public abstract class Customer extends User {
    private String vatNumber;
    private int numberOfAcounts;
    private String email;
    private String phoneNumber;
    private String rf; // Προσθήκη πεδίου RF κωδικού

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    private String afm;

    public Customer(String userID, String username, String password, UserRole CUSTOMER, String VAT, String email, String phoneNumber, String afm) {
        super(userID, username, password, CUSTOMER);
        this.numberOfAcounts = 0;
        this.vatNumber = VAT;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.afm = afm;
        this.rf = generateRF(); // Αυτόματη δημιουργία RF κατά την κατασκευή
    }

    // Προσθήκη getter για RF
    public String getRF() {
        return rf;
    }

    // Προσθήκη setter για RF
    public void setRF(String rf) {
        this.rf = rf;
    }

    // Μέθοδος για αυτόματη δημιουργία RF κωδικού
    private String generateRF() {
        return "RF" + String.format("%06d", (int)(Math.random() * 1000000));
    }

    public String getVAT() {
        return vatNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNumberOfAcounts() {
        return numberOfAcounts;
    }

    public abstract String getFullName();

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumberOfAcounts(int numberOfAcounts) {
        this.numberOfAcounts = numberOfAcounts;
    }

    public String getEmail() {
        return email;
    }

    void setVAT(String VAT) {
        this.vatNumber = VAT;
    }
}