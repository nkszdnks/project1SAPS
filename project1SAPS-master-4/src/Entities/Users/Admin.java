package Entities.Users;


public class Admin extends User {

    private String employeeId;

    public Admin(String userId, String username, String passwordHash, String employeeId) {

        super(userId, username, passwordHash, UserRole.ADMIN);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() { return employeeId; }

    @Override
    public String marshal() {
        return  "userId:" + getUserId() +
                ",username:" + getUsername() +
                ",passwordHash:" + getPasswordHash() +
                ",email:" +
                ",phone:" +
                ",role:ADMIN" +
                ",firstName:" +
                ",lastName:" +
                ",afm:" +
                ",lastLogin:"+String.valueOf(getLastLogin()) +
                ",businessName:" +
                ",employeeId:" + employeeId +
                ",vatNumber:";
    }

/*
    public static Admin unmarshal(String[] data) {
        return new Admin(
                data[0], data[1], data[2], data[10]
        );
    }
*/

}
