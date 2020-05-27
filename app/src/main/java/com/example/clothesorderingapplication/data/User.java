package com.example.clothesorderingapplication.data;

import com.example.clothesorderingapplication.api.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String username;
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private Type type;
    private String ManagerCode;

    public static User fromJSONObject(JSONObject json){
        try {
            User user = new User();
            user.name = json.getString("Name");
            user.id = json.getString("Id");
            user.type = Utils.intToType(json.getInt("Type"));
            user.email = json.getString("Email");
            user.ManagerCode = json.getString("Code Manager");
            user.phoneNumber = json.getString("PhoneNumber");
            return user;
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getManagerCode() {
        return ManagerCode;
    }

    public void setManagerCode(String managerCode) {
        ManagerCode = managerCode;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
