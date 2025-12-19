package Entities.Users;

import Entities.Accounts.BusinessAcount;

public class Business extends Customer {

    private String businessName;
    private BusinessAcount corporateAcount;

    public Business(String userId, String username, String passwordHash,
                    String email, String phone,
                    String businessName, String afm,String vatNumber) {

        super(userId, username, passwordHash,UserRole.BUSINESS,vatNumber,email, phone,afm);
        this.businessName = businessName;
        this.corporateAcount = corporateAcount;
    }

    public String getBusinessName() { return businessName; }
    public BusinessAcount getCorporateAcount() { return corporateAcount; }


    public void setCorporateAcount(BusinessAcount corporateAcount) {
        this.corporateAcount = corporateAcount;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    public String getAfm() {
        return super.getAfm();
    }

    @Override
    public String getFullName() {
        return getBusinessName();
    }

    @Override
    public String marshal() {
        return  "userId:" + getUserId() +
                ",username:" + getUsername() +
                ",passwordHash:" + getPasswordHash() +
                ",email:" + getEmail() +
                ",phone:" + getPhoneNumber() +
                ",role:BUSINESS" +
                ",firstName:" +
                ",lastName:" +
                ",afm:" +
                ",businessName:" + getBusinessName() +
                ",employeeId:" +
                ",vatNumber:" + getVAT();
    }




}
