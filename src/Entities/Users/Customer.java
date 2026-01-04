package Entities.Users;



public abstract class Customer extends User {
    private String vatNumber;
    private int numberOfAcounts;
    private String email;
    private String phoneNumber;

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    private String afm;
    public Customer(String userID,String username,String password, UserRole CUSTOMER, String VAT,String email, String phoneNumber,String afm) {
        super(userID,username,password, CUSTOMER);
        this.numberOfAcounts = 0;
        this.vatNumber = VAT;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.afm = afm;
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
    /* Only for users
    public String marshal() {
        return String.valueOf("type:"+getType()+",legalName:"+getOwner()+",userName:"+getUsername()+",password:"+getPassword()+",vatNumber:"+getVAT());
    }
*/
    /* Only for users
    public void unmarshal(String data) {
        String[] dataList = data.split(",");
        for (String dataType : dataList) {
            String[] dataTypes = dataType.split(":");
            switch (dataTypes[0]) {
                case "type":
                    this.setType(String.valueOf(dataTypes[1]));
                    break;
                case "legalName":
                    this.setOwner(String.valueOf(dataTypes[1]));
                    break;
                case "userName":
                    this.setUsername(String.valueOf(dataTypes[1]));
                    break;
                case "password":
                    this.setPassword(String.valueOf(dataTypes[1]));
                    break;
                case "vatNumber":
                    this.setVAT(String.valueOf(dataTypes[1]));
                    break;
            }
        }
    }

     */


}
