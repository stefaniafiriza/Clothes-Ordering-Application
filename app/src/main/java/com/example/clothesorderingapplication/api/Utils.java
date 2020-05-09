package com.example.clothesorderingapplication.api;


import android.util.JsonReader;

import com.example.clothesorderingapplication.data.Type;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    static String api_key = "";
    static String url = "http://192.168.0.171:8080";


    public static JSONObject responseToJSON(String response) {
        try {
            return new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Type intToType(int in) {
        if (in == 0)
            return Type.CUSTOMER;
        if (in == 1)
            return Type.MANAGER;
        return Type.ADMIN;
    }

    public static int typeToInt(Type type) {
        if (type == Type.CUSTOMER)
            return 0;
        if (type == Type.MANAGER)
            return 1;
        return 2;
    }

}
