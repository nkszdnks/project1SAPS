package Entities.Users;


import java.util.HashMap;

public abstract class User {

    private String userId;
    private String username;
    private String passwordHash;
    private UserRole role;

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
                return new IndividualPerson(
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

            case BUSINESS:
                return new Business(
                        map.get("userId"),
                        map.get("username"),
                        map.get("passwordHash"),
                        map.get("email"),
                        map.get("phone"),
                        map.get("businessName"),
                        map.get("afm"),
                        map.get("vatNumber")
                );

            case ADMIN:
                return new Admin(
                        map.get("userId"),
                        map.get("username"),
                        map.get("passwordHash"),
                        map.get("employeeId")
                );

            default:
                throw new RuntimeException("Unknown role in CSV: " + role);
        }
    }


}

