package com.example.jewelleryapp.Retrofit;

import com.example.jewelleryapp.Model.UserLogin;
import com.google.gson.annotations.SerializedName;

public class Res {
    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResultMsg")
    private String ResponseMsg;

     @SerializedName("UserLogin")
     UserLogin userLogin;

    public Res(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public Res(String responseCode, String responseMessage, String result, String ResponseMsg) {
        this.ResponseCode = responseCode;
        this.Result = result;
        this.ResponseMsg = ResponseMsg;
    }

    public String getResponseMsg() {
        return ResponseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        ResponseMsg = responseMsg;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
