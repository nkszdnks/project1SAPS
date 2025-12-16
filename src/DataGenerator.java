import Managers.BillManager;

import java.io.*;
import java.util.*;

public class DataGenerator {

    // ------ CONFIG ------
    static int NUM_PERSON_USERS = 200;
    static int NUM_BUSINESS_USERS = 200;
    static int NUM_ADMIN_USERS = 3;
    static int NUM_ACCOUNTS = 5500;

    public static void main(String[] args) throws Exception {

        Random r = new Random();
        List<String> userIds = new ArrayList<>();

        PrintWriter usersFile = new PrintWriter("users.csv");
        PrintWriter accountsFile = new PrintWriter("accounts.csv");

        // -------- USERS HEADER --------

        // -------- GENERATE PERSON USERS --------
        for (int i = 0; i < NUM_PERSON_USERS; i++) {
            String id = "U" + String.format("%03d", i + 1);
            userIds.add(id);

            String username = "User" + (i + 1);
            String phone = "690000" + String.format("%03d", i);
            String email = username + "@example.com";
            String afm = String.valueOf(100000000 + r.nextInt(900000000));
            String vat = "CVAT" + String.format("%05d", i);

            usersFile.println(String.join(",",
                    "userId:" + id,
                    "username:" + username,
                    "passwordHash:hash",
                    "email:" + email,
                    "phone:" + phone,
                    "role:PERSON",
                    "firstName:First" + i,
                    "lastName:Last" + i,
                    "afm:" + afm,
                    "businessName:",
                    "employeeId:",
                    "vatNumber:" + vat
            ));
        }

        // -------- GENERATE BUSINESS USERS --------
        for (int i = 0; i < NUM_BUSINESS_USERS; i++) {
            String id = "BIZ" + String.format("%05d", i);
            userIds.add(id);

            String username = "business" + String.format("%05d", i);
            String email = username + "@example.com";
            String phone = "210000" + String.format("%04d", i);
            String vat = "IVAT" + String.format("%05d", i);

            usersFile.println(String.join(",",
                    "userId:" + id,
                    "username:" + username,
                    "passwordHash:hash",
                    "email:" + email,
                    "phone:" + phone,
                    "role:BUSINESS",
                    "firstName:",
                    "lastName:",
                    "afm:",
                    "businessName:Business " + i,
                    "employeeId:",
                    "vatNumber:" + vat
            ));
        }

        // -------- GENERATE ADMIN USERS --------
        for (int i = 0; i < NUM_ADMIN_USERS; i++) {
            String id = "ADM" + i;
            userIds.add(id);

            usersFile.println(String.join(",",
                    "userId:" + id,
                    "username:admin" + i,
                    "passwordHash:hash",
                    "email:admin" + i + "@example.com",
                    "phone:",
                    "role:ADMIN",
                    "firstName:",
                    "lastName:",
                    "afm:",
                    "businessName:",
                    "employeeId:EMP" + i,
                    "vatNumber:"
            ));

        }

        usersFile.close();


        // -------- ACCOUNTS HEADER --------

        // -------- GENERATE ACCOUNTS --------
        for (int i = 0; i < NUM_ACCOUNTS; i++) {

            String accId = "ACC" + String.format("%03d", i);
            String iban = "GR" + (1000000000 + r.nextInt(900000000));
            String interest = "1.0";
            String balance = String.format("%.2f", r.nextDouble() * 10000);
            String date = "2024-" + String.format("%02d", 1 + r.nextInt(12)) + "-" + String.format("%02d", 1 + r.nextInt(28));
            String type = (i % 2 == 0) ? "PERSONAL" : "BUSINESS";

            String mainOwner = "U001";
            // choose a main owner
            if(type.equals("PERSONAL")) {

                for (int j = 0; j < NUM_PERSON_USERS; j++) {
                    mainOwner = userIds.get(r.nextInt(userIds.size()));
                    if (mainOwner.startsWith("U")) {
                        break;
                    }
                }
            }
            else {
                for (int j = 0; j < NUM_BUSINESS_USERS; j++) {
                    mainOwner = userIds.get(r.nextInt(userIds.size()));
                    if (mainOwner.startsWith("B")) {
                        break;
                    }
                }
            }
            String secondaryStr;
            // secondary owners (0–3 random users)
            int secCount = r.nextInt(4);
            if(type.equals("PERSONAL")) {
                Set<String> secondaryOwners = new HashSet<>();
                for (int s = 0; s < secCount; s++) {
                    String selected = userIds.get(r.nextInt(userIds.size()));
                    if (!selected.equals(mainOwner) && selected.startsWith("U")) {
                        secondaryOwners.add(selected);
                    }
                }

                secondaryStr = String.join("|", secondaryOwners);
            }
            else{
                 secondaryStr = "";
            }
            String maintenanceFee = (type.equals("BUSINESS")) ? "5.0" : "";

            accountsFile.println(String.join(",",
                    "AccountID:" + accId,
                    "IBAN:" + iban,
                    "InterestRate:" + interest,
                    "Balance:" + balance,
                    "DateCreated:" + date,
                    "AccountType:" + type,
                    "MainOwnerID:" + mainOwner,
                    "SecondaryOwnerIDs:" + secondaryStr,
                    "MaintenanceFee:" + maintenanceFee
            ));

        }

        accountsFile.close();

        System.out.println("Generated users.csv and accounts.csv!");
        BillManager.getInstance().restore();
        System.out.println(BillManager.getInstance().findBill("RF00000"));
    }
}
