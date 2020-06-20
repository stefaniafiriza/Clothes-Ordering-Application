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

    private String getHashedPassword(String password) {
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

    private void call(String url, final ICallback callback) {
        queue.add(new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onFinish(response, context);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error, context);
                    }
                })
        );
    }

    public void login(String username, String password, final ICallback loginCallback) {
        String url = createURL("/api/login",
                parametersToURL(new String[]{"username", "password"},
                        new String[]{username, getHashedPassword(password)}));
        call(url, loginCallback);
    }

    public void register(String username, String password, String name, String email, String phoneNumber, String codeManager, final ICallback registerCallback) {
        String type = Integer.toString(Utils.typeToInt(Type.CUSTOMER));
        if (!codeManager.equals("")) {
            type = Integer.toString(Utils.typeToInt(Type.MANAGER));
        } else {

            codeManager = "";
        }
        String url = createURL("/api/register", parametersToURL(
                new String[]{"username", "password", "name", "email", "type", "phoneNumber", "codeManager"},
                new String[]{username, getHashedPassword(password), name, email, type, phoneNumber, codeManager}
        ));

        call(url, registerCallback);

    }

    public void search(String product, final ICallback searchCallback) {
        String url = createURL("/api/search", parametersToURL(new String[]{"name"}, new String[]{product}));
        call(url, searchCallback);
    }

    public void createShoppingCartID(String userID, final ICallback shoppingCartCallback) {
        String url = createURL("/api/createShoppingCartID", parametersToURL(new String[]{"userID"}, new String[]{userID}));
        call(url, shoppingCartCallback);
    }

    public void addToCart(String cartID, String productID, String amount, final ICallback addToCartCallback) {
        String url = createURL("/api/addToCart", parametersToURL(new String[]{"cartID", "productID", "amount"}, new String[]{cartID, productID, amount}));
        call(url, addToCartCallback);
    }

    public void removeFromCart(String cartID, String productID, String amount, final ICallback removeFromCartCallback) {
        String url = createURL("/api/removeFromCart", parametersToURL(new String[]{"cartID", "productID", "amount"}, new String[]{cartID, productID, amount}));
        call(url, removeFromCartCallback);
    }

    public void addProduct(String name, String type, String size, String price, String stock, String description, final ICallback addProductCallback) {
        String url = createURL("/api/addProduct", parametersToURL(
                new String[]{"name", "type", "size", "price", "stock", "description"},
                new String[]{name, type, size, price, stock, description}));
        call(url, addProductCallback);
    }

    public void getProducts(final ICallback getProductsCallback) {
        String url = createURL("/api/getProducts", parametersToURL(new String[]{}, new String[]{}));
        call(url, getProductsCallback);
    }

    public void getShoppingCart(String cartID, final ICallback getShoppingCartCallback) {
        String url = createURL("/api/getShoppingCart", parametersToURL(new String[]{"cartID"}, new String[]{cartID}));
        call(url, getShoppingCartCallback);
    }

    public void verifyUser(String username, final ICallback verifyUserCallback) {
        String url = createURL("/api/verifyUser", parametersToURL(new String[]{"username"}, new String[]{username}));
        call(url, verifyUserCallback);
    }

    public void makeOrder(String cartID, final ICallback orderCallback) {
        String url = createURL("/api/order", parametersToURL(new String[]{"cartID"}, new String[]{cartID}));
        call(url, orderCallback);
    }

    public void nextStepForOrder(String orderID, final ICallback nextStepCallback) {
        String url = createURL("/api/orderNextStep", parametersToURL(new String[]{"orderID"}, new String[]{orderID}));
        call(url, nextStepCallback);
    }

    public void getOrderByUserID(String userID, final ICallback orderByUserIDCallback) {
        String url = createURL("/api/getOrderByUserID", parametersToURL(new String[]{"userID"}, new String[]{userID}));
        call(url, orderByUserIDCallback);
    }

    public void deleteProduct(String productName, final ICallback deleteProductCallback) {
        String url = createURL("/api/deleteProduct", parametersToURL(new String[]{"name"}, new String[]{productName}));
        call(url, deleteProductCallback);
    }

    public void editProduct(String id, String name, String type, String size, String price, String stock, String description,final ICallback editProductCallback){
        String url = createURL("/api/editProduct", parametersToURL(
                new String[]{"id","name", "type", "size", "price", "stock", "description"},
                new String[]{id,name, type, size, price, stock, description}));
        call(url, editProductCallback);
    }
}
