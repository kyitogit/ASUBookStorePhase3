package com.example.group31finalproject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class userDatabaseControl {
    //path  to user database used
    private final String filePath = "src/main/resources/com/example/group31finalproject/userDatabase.csv";
    private final List<User> userList;

    public userDatabaseControl() {
        userList = getUserList();
    }

    // Method to read users from CSV
    public List<User> getUserList() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) { // username, password, type
                    users.add(new User(data[0], data[1], data[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Add a user to the CSV userDatabase
    public void addUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.formatUser());
            writer.newLine();
            userList.add(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(User user) {
        //if username given to  be  removed  matches name in database then they are  removed  from  user  list
        if (userList.removeIf(u -> u.getUsername().equals(user.getUsername()))) {
            //database updated through userlist
            updateUserDatabase();
        } else {
            System.out.println("User not found in the list.");
        }
    }

    //  updates USerDatabase  based on current userlist
    private void updateUserDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : userList) {
                writer.write(user.formatUser());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if the username already exists
    public boolean usernameExists(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Method to check if username exists and password matches . Intended to be  used after usernameExists
    public boolean validateKnownUserPassword(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Username exists and password matches
            }
        }
        return false; // Either username doesn't exist or password doesn't match
    }

    public boolean changeUserPassword(String username, String newPassword, boolean adminSystemPermission) {
        if (!adminSystemPermission) {
            System.out.println("Permission denied");
            return false;
        }

        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                user.setPassword(newPassword); // Updates the User object directly in the list
                updateUserDatabase(); // Writes the updated list to the CSV file
                System.out.println("Password for " + username + " has been updated.");
                return true;
            }
        }

        System.out.println("User not found.");
        return false;
    }

}

