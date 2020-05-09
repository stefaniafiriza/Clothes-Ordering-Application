package com.example.clothesorderingapplication.api;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clothesorderingapplication.api.interfaces.ICallback;
import com.example.clothesorderingapplication.data.Type;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class API {
    private RequestQueue queue;
    public final Context context;
    public API(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    private String createURL(String path, String parameters) {
        return Utils.url + path + parameters;
    }

    private String parametersToURL(String[] parametersNames, String[] parameters) {
        StringBuilder str = new StringBuilder("?");
        str.append("key=").append(Utils.api_key).append("&");
        for (int i = 0; i < parametersNames.length; i++) {
            str.append(parametersNames[i]).append("=").append(parameters[i]).append("&");
        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }

    private static byte[] getSalt() {
        return "SaltedPassword".getBytes();
    }

    private String getHashedPassword(String password){
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), getSalt(), 1000, 256);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return new BigInteger(1, skf.generateSecret(spec).getEncoded()).toString(16);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void login(String username, String password, final ICallback loginCallback){
        String url = createURL("/api/login",
                parametersToURL(new String[]{"username", "password"},
                                new String[]{username, getHashedPassword(password)}));
            queue.add(new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loginCallback.onFinish(response, context);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                        }
                    })
            );
    }

    public void register(String username, String password, String name, String email, String phoneNumber, String codeManager,final ICallback registerCallback) {
        String type = Integer.toString(Utils.typeToInt(Type.CUSTOMER));
        if (!codeManager.equals("")){
            type = Integer.toString(Utils.typeToInt(Type.MANAGER));
        }else{
            codeManager = "0";
        }
        String url = createURL("/api/register", parametersToURL(
                new String[]{"username", "password", "name", "email", "type", "phoneNumber", "codeManager"},
                new String[]{username, getHashedPassword(password), name, email, type, phoneNumber, codeManager}
        ));
        queue.add(new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        registerCallback.onFinish(response, context);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                })
        );
    }

}
