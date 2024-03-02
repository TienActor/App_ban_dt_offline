package com.example.doan_sale.model;
import java.io.Serializable;

public class user implements Serializable {
    private String userName;
    private int userID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    private String password;
    private String email;
    private String phoneNumber;
    private int avatar;
    public String getUserName() {
        return userName;
    }
    public user(int userID,String userName, String password, String email, String phoneNumber,int avatar) {
        this.userName = userName;
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
    }
    public user(String userName,String password, String email, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
public user(){}


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public user(String username, int avatar) {
        this.userName = username;
        this.avatar = avatar;
    }
}
