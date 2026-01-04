package Entities.Users;


import java.time.LocalDateTime;
import java.util.HashMap;

public abstract class User {

    private String userId;
    private String username;
    private String passwordHash;
    private UserRole role;
    private LocalDateTime lastLogin;

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public User(String userId, String username, String passwordHash, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;

    }

    public String getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public UserRole getRole() {
        return role;
    }
    public abstract String marshal();        // to CSV

    public static User unmarshal(String csvLine) {
        String[] data = csvLine.split(",",-1);

        HashMap<String,String> map = new HashMap<>();

        for (String c : data) {
            String[] kv = c.split(":", 2);
            if (kv.length == 2) map.put(kv[0], kv[1]);
        }
        UserRole role = UserRole.valueOf(map.get("role"));

        switch (role) {
            case PERSON:
                IndividualPerson person = new IndividualPerson(
                        map.get("userId"),
                        map.get("username"),
                        map.get("passwordHash"),
                        map.get("email"),
                        map.get("phone"),
                        map.get("firstName"),
                        map.get("lastName"),
                        map.get("afm"),
                        map.get("vatNumber")
                );
                person.setLastLogin(LocalDateTime.parse(map.get("lastLogin")));
                return person;

            case BUSINESS:
                Business business = new Business(
                        map.get("userId"),
                        map.get("username"),
                        map.get("passwordHash"),
                        map.get("email"),
                        map.get("phone"),
                        map.get("businessName"),
                        map.get("afm"),
                        map.get("vatNumber")
                );
                business.setLastLogin(LocalDateTime.parse(map.get("lastLogin")));
                return business;

            case ADMIN:
                Admin admin = new Admin(
                        map.get("userId"),
                        map.get("username"),
                        map.get("passwordHash"),
                        map.get("employeeId")
                );
                admin.setLastLogin(LocalDateTime.parse(map.get("lastLogin")));
                return admin;

            default:
                throw new RuntimeException("Unknown role in CSV: " + role);
        }
    }


}

