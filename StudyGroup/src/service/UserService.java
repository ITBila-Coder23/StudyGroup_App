package service;

import model.User;
import storage.Database;

import java.util.Optional;

public class UserService {
    private Database database;

    public UserService(Database database) {
        this.database = database;
    }

    /**
     * Registers a new user with the given username and password.
     * @param username the username of the new user
     * @param password the password of the new user
     * @return true if registration is successful, false if user already exists
     */
    public boolean registerUser(String username, String password) {
        try {
            if (database.getUserByUsername(username).isPresent()) {
                return false; // User already exists
            }
            User user = new User(username, password);
            database.saveUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Logs in a user with the given username and password.
     * @param username the username of the user
     * @param password the password of the user
     * @return the User object if login is successful, null otherwise
     */
    public User loginUser(String username, String password) {
        try {
            Optional<User> userOptional = database.getUserByUsername(username);
            if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
                return userOptional.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
