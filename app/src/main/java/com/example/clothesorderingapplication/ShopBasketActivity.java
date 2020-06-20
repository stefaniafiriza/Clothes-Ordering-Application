package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.interfaces.ICallback;
import com.example.clothesorderingapplication.data.Product;
import com.example.clothesorderingapplication.data.ProductAdapter;
import com.example.clothesorderingapplication.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class ShopBasketActivity extends AppCompatActivity {

    protected Button Back;
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected Button order, orderStatus;
    protected TextView empty_cart, total;
    LinkedList<Product> productList = new LinkedList<>();
    LinkedList<Long> amountOfItems = new LinkedList<>();
    final ProductAdapter productAdapter = new ProductAdapter(this.productList, this.amountOfItems, this);;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_basket);
        api = new API(this);
        Back = findViewById(R.id.btn_back);
        empty_cart = findViewById(R.id.empty_cart);
        recyclerView = findViewById(R.id.cart_list);
        order = findViewById(R.id.btn_order);
        total = findViewById(R.id.total_price);
        orderStatus = findViewById(R.id.btn_order_status);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        empty_cart.setVisibility(View.INVISIBLE);
        orderStatus.setVisibility(View.INVISIBLE);

        this.UpdateList();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopBasketActivity.this, HomeActivity.class));
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.makeOrder(
                        User.logged_in_user.getShoppingCartID(),
                        new ICallback() {
                            @Override
                            public void onFinish(String response, Context context) {
                                if(response.contains("error")){
                                    Toast.makeText(ShopBasketActivity.this, "The order could not be made. Please try again later.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ShopBasketActivity.this, "The order successfully registered", Toast.LENGTH_SHORT).show();
                                    order.setVisibility(View.INVISIBLE);
                                    orderStatus.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onError(VolleyError error, Context context) {
                                Toast.makeText(ShopBasketActivity.this, "The order could not be made. Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

            }
        });

        orderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopBasketActivity.this,OrderStatusActivity.class));
            }
        });
    }

    public void UpdateList(){
        recyclerView.setAdapter(productAdapter);
        api.getShoppingCart(
                User.logged_in_user.getShoppingCartID(),
                new ICallback() {

                    LinkedList<Long> cartHelper(JSONArray array){
                        LinkedList<Long> list= new LinkedList<>();
                        for(int i =0; i < array.length(); i ++){
                            try {
                                list.add(array.getLong(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return list;
                    }

                    @Override
                    public void onFinish(String response, Context context) {
                        if(response.contains("null") || response.contains("{}")){
                            // the cart has nothing in it, skip
                            return;
                        }

                        response = response.substring(response.indexOf('[') + 2, response.indexOf(']') - 1);
                        response = response.replaceAll("\\{", "[");
                        response = response.replaceAll("\\}", "]");
                        response = "{" + response + "}";
                        try {
                            JSONObject json = new JSONObject(response);
                            LinkedList<Long> cartItems = cartHelper(json.getJSONArray("Cart"));
                            LinkedList<Long> amountOfItems = cartHelper(json.getJSONArray("Ammounts"));

                            for(Long l : cartItems){
                                for(Product p : Product.products){
                                    if(p.getId().equals(l + "")){
                                        ((ShopBasketActivity)context).productList.add(p);
                                        break;
                                    }
                                }
                            }
                            for(Long l : amountOfItems){
                                ((ShopBasketActivity)context).amountOfItems.add(l);
                            }

                            float total_price = 0.0f;
                            for(int i = 0; i<productList.size(); i++){
                                Product p = productList.get(i);

                                total_price += Float.parseFloat(p.getPrice()) * amountOfItems.get(i);
                            }
                            String s = "Total :" + total_price + "€";
                            ((ShopBasketActivity)context).total.setText(s);

                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(VolleyError error, Context context) {

                    }
                }
        );

    }
}
