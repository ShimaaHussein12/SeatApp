package com.example.seats.Model;

public class User {
    private String id;
    private String Username,UserEmail,UserMobileNumber,token,UserPassword;
//nationid
    public User() {
    }

    public User(String token) {
        this.token = token;
    }

    public User(String id, String username, String userEmail, String token, String userPassword,String userMobileNumber ) {
        this.id = id;
        Username = username;
        UserEmail = userEmail;
       UserMobileNumber = userMobileNumber;
        this.token = token;
        UserPassword = userPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserMobileNumber() {
        return UserMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        UserMobileNumber = userMobileNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }
}
