package Entities.Users;


import java.util.HashMap;

public class IndividualPerson extends Customer {

    private String firstName;
    private String lastName;


    public IndividualPerson(String userId, String username, String passwordHash,
                            String email, String phone,
                            String firstName, String lastName, String afm,String vatNumber) {

        super(userId, username, passwordHash,UserRole.PERSON,vatNumber,email, phone,afm);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    @Override
    public String marshal() {
        return  "userId:" + getUserId() +
                ",username:" + getUsername() +
                ",passwordHash:" + getPasswordHash() +
                ",email:" + getEmail() +
                ",phone:" + getPhoneNumber() +
                ",role:PERSON" +
                ",firstName:" + getFirstName() +
                ",lastName:" + getLastName() +
                ",afm:" + getAfm() +
                ",lastLogin:"+String.valueOf(getLastLogin()) +
                ",businessName:" +
                ",employeeId:" +
                ",vatNumber:" + getVAT();
    }

    @Override
    public String getFullName() {
        return getFirstName()+" "+getLastName();
    }

/*
    public  static IndividualPerson unmarshal(HashMap<String,String> map) {
        return new IndividualPerson(
                data[0], data[1], data[2], data[3], data[4],
                data[6], data[7],data[8],data[11]
        );
    }
 */
    /*
    public  IndividualPerson unmarshal(String[] data) {
        String[] dataList;
        dataList = data.split(",");
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
            }
        }
    }
*/

}
