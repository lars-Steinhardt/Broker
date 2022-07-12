package ch.bzz.broker.model;

import java.util.List;

public class User {
    private String userUUID;
    private String username;
    private String password;
    private String role;
    private List<String> words;

    public User() {
        setRole("guest");
    }

    /**
     * Gets the UserUUID
     * @return value of userUUID
     */
    public String getUserUUID() {
        return userUUID;
    }

    /**
     * Sets the userUUID
     * @param userUUID
     */
    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    /**
     * Gets the username
     * @return value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password
     * @return value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the Role
     * @return value of role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }
    /**
     * Gets the words
     * @return value of words
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * Sets the words
     * @param words
     */
    public void setWords(List<String> words) {
        this.words = words;
    }
}
