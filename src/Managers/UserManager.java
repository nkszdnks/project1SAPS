package Managers;


import DataAccessObjects.FactoryDAO;
import DataAccessObjects.UsersDAO;
import Entities.Users.*;
import Entities.Users.User;
//import storage.MyCollection;
//import storage.Storager;

import java.util.LinkedList;
import java.util.List;

public class UserManager implements Manager {
    private static  UserManager INSTANCE;
    private FactoryDAO factoryDAO ;
    private UsersDAO usersDAO ;
    private List<User> users = new LinkedList<>();

   private UserManager() {
       factoryDAO = FactoryDAO.getInstance();
       usersDAO = factoryDAO.getUsersDAO();
   }



    public static UserManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserManager();
            return INSTANCE;
        }
        return INSTANCE;
    }

    public List<User> getUsers() {
        return users;
    }
    public List<String> getBusinessesNames() {
        List<String> businesses = new LinkedList<>();
        for (User user : users) {
            if(user.getRole().equals(UserRole.BUSINESS)){
                businesses.add(((Business)user).getBusinessName());
            }
        }
        return businesses;
    }

    public User identifyUser(String userName, String password) {
        User user = findUser(userName);
        if(user != null && user.getPasswordHash().equals(password)){
            return user;
        }
        return null;
    }

    public Customer findCustomerByID(String userID) {
        Customer customer;
        for (User usr : users) {
            if (usr.getRole().equals(UserRole.BUSINESS) || usr.getRole().equals(UserRole.PERSON)) {
                customer = (Customer) usr;
                if (customer.getUserId().equals(userID))
                    return customer;
            }
        }
        return null;
    }

    public IndividualPerson findUserByFullName(String fullName) {
        IndividualPerson p;
        for (User usr : users) {
            if (usr.getRole().equals(UserRole.PERSON)) {
                p = (IndividualPerson) usr;
                if ((p.getFirstName()+p.getLastName()).equals(fullName))
                    return p;
            }
        }
        return null;
    }

    public Customer findCustomerByVAT(String VAT) {
        Customer customer;
        for (User usr : users) {
            if (usr.getRole().equals(UserRole.BUSINESS) || usr.getRole().equals(UserRole.PERSON)) {
                customer = (Customer) usr;
                if (customer.getVAT().equals(VAT))
                    return customer;
            }
        }
        return null;
    }


    public User findUser(String userName) {
        for (User usr : users) {
           if(usr.getUsername().equals(userName)){
               return usr;
           }
        }
        return null;
    }

    public void registerUser(User user) {
        users.add(user);
    }

    public void updateUser(String userName, User user) {
        User user2 = findUser(userName);
        for (User usr : users) {
            if(usr.getUsername().equals(user2.getUsername())){
               usr = user;
               return;
            }
        }
    }

    @Override
    public void restore() {
        users = usersDAO.loadUsers();
    }

    @Override
    public void save() {
        usersDAO.saveUsers(users);

    }

}
