package com.example.jewelleryapp.Model;

import com.google.gson.annotations.SerializedName;

public class UserLogin {
    private String name;

    private String email;

    private String phone;

    private String pass;

    private int id;

    private String status;

    private String imgpath;

    private String deleted;

    private String dt;

    private String userType;

    public UserLogin(String name, String email, String phone, String pass, int id, String status, String imgpath, String deleted, String dt, String userType) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
        this.id = id;
        this.status = status;
        this.imgpath = imgpath;
        this.deleted = deleted;
        this.dt = dt;
        this.userType = userType;
    }

    public UserLogin() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
