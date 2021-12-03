package com.example.jewelleryapp.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    private String phone;
    private String pass;
    private UserLogin userLogin;

    public User(String phone, String pass, UserLogin userLogin) {
        this.phone = phone;
        this.pass = pass;
        this.userLogin = userLogin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }
}
