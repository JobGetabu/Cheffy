package com.app.cheffyuser.create_account.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {


    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("token")
    private String token;

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public LoginData(String login, String password) {
        this.login = login;
        this.password = password;
    }






    public void setToken(String token)
    {
        this.token=token;
    }
    public String getToken()
    {
        return token;
    }


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
