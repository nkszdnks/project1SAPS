package DataAccessObjects;

import Entities.Users.User;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class UsersDAO {
    private  String usersFile;

    public String getUsersFile() {
        return usersFile;
    }

    public void setUsersFile(String usersFile) {
        this.usersFile = usersFile;
    }

    public UsersDAO(String usersFile) {
     this.usersFile = usersFile;
    }

    public List<User> loadUsers() {
        List<User> users = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {

            String line;
           //oolean first = true;

            while ((line = br.readLine()) != null) {

             // if (first) { first = false; continue; } // skip header
                if (line.trim().isEmpty()) continue;
                User user = User.unmarshal(line);
                users.add(user);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return users;
    }
    public void saveUsers(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(usersFile))) {
           //w.println("userId(0),username(1),passwordHash(2),email(3),phone(4),role(5),firstName(6),lastName(7),afm(8),businessName(9),employeeId(10),vatNumber(11)");
            for (User u : users) {
                pw.println(u.marshal());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
