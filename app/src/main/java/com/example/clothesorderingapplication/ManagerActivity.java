package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.interfaces.ICallback;
import com.example.clothesorderingapplication.data.ManagerAdapter;
import com.example.clothesorderingapplication.data.Product;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {


    protected MenuItem menuLogOut;
    protected Button managerItem, managerOrder;
    protected Button[] accept = new Button[6];
    protected TextView[] orderIDS = new TextView[6];
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        managerItem = findViewById(R.id.manager_items);

        loadingBar = new ProgressDialog(this);
        loadingBar.setTitle("Update products.");
        loadingBar.setMessage("Please wait, while we're retrieving the products.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final API api = new API(this);

        api.getProducts(new ICallback() {
            @Override
            public void onFinish(String response, Context context) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Product.products.clear();
                    for(int i =0; i < jsonArray.length(); i ++){
                        Product.products.add(Product.fromJSONObject(jsonArray.getJSONObject(i)));
                    }
                    loadingBar.dismiss();
                    return;
                } catch (JSONException e) {
                    loadingBar.dismiss();
                    e.printStackTrace();
                }

                Toast.makeText(ManagerActivity.this,"Failed to retrieve products from the server.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VolleyError error, Context context) {

            }
        });

        managerOrder = findViewById(R.id.manager_orders);

        managerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ManagerAdapter managerAdapter = new ManagerAdapter(Product.products, v.getContext());
                setContentView(R.layout.manager_items);
                recyclerView = findViewById(R.id.manager_list);
                recyclerView.setHasFixedSize(false);
                layoutManager = new LinearLayoutManager(v.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(managerAdapter);

                ImageButton add_new_product = findViewById(R.id.add_product);
                add_new_product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edit(api);
                    }
                });
            }
        });


        managerOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.manager_orders);

                accept[0] =findViewById (R.id.accept1);
                accept[1] =findViewById (R.id.accept2);
                accept[2] =findViewById (R.id.accept3);
                accept[3] =findViewById (R.id.accept4);
                accept[4] =findViewById (R.id.accept5);
                accept[5] =findViewById (R.id.accept6);

                orderIDS[0] =findViewById (R.id.order1);
                orderIDS[1] =findViewById (R.id.order2);
                orderIDS[2] =findViewById (R.id.order3);
                orderIDS[3] =findViewById (R.id.order4);
                orderIDS[4] =findViewById (R.id.order5);
                orderIDS[5] =findViewById (R.id.order6);

                api.getOrders(new ICallback() {
                    @Override
                    public void onFinish(String response, Context context) {


                        if(response.contains("error")){
                            Toast.makeText(ManagerActivity.this,"Failed to retrieve orders from the server.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try{
                            String pattern = "\\{((\\d+,)+\\d+)\\}";
                            response = response.replaceAll(pattern, "[$1]");
                            pattern = "\\{(\\d+)\\}";
                            response = response.replaceAll(pattern, "[$1]");


                            JSONArray ja = new JSONArray(response);
                            int k = 0;
                            for(int i = 0; i < ja.length(); i++){
                                if(k > 5){
                                    break;
                                }
                                final String id = ja.getJSONObject(i).getString("Id");
                                final String status = ja.getJSONObject(i).getString("Status");
                                if(!status.equals("0"))
                                    continue;

                                orderIDS[k].setText(id);
                                accept[k].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        api.nextStepForOrder(id, new ICallback() {
                                            @Override
                                            public void onFinish(String response, Context context) {
                                                if(response.contains("error")){
                                                    Toast.makeText(ManagerActivity.this,"Failed to accept the order.", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                Toast.makeText(ManagerActivity.this,"Accepted the order.", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onError(VolleyError error, Context context) {

                                            }
                                        });
                                    }
                                });
                                k++;
                            }

                            for(; k <= 5; k++){
                                orderIDS[k].setVisibility(View.INVISIBLE);
                                accept[k].setVisibility(View.INVISIBLE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(VolleyError error, Context context) {

                    }
                });


            }
        });
    }
    public void edit(final API api){
        setContentView(R.layout.edit_product);
        final EditText name, type, size, price, stock, desc;

        name = findViewById(R.id.name_edit);
        type = findViewById(R.id.type_edit);
        size = findViewById(R.id.size_edit);
        price = findViewById(R.id.price_edit);
        stock = findViewById(R.id.stock_edit);
        desc = findViewById(R.id.description_edit);
        Button save = findViewById(R.id.save_man);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.addProduct(name.getText().toString(), type.getText().toString(),
                        size.getText().toString(), price.getText().toString(),
                        stock.getText().toString(), desc.getText().toString(), new ICallback() {
                            @Override
                            public void onFinish(String response, Context context) {
                                if(response.contains("error")){
                                    Toast.makeText( context, "Could not add the item to the database.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText( context, "Added the item in the database.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(VolleyError error, Context context) {
                                Toast.makeText( context, "Could not add the item to the database.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.manager_menu, menu);
        menuLogOut = menu.findItem(R.id.log_out);

        menuLogOut.setActionView(R.layout.log_out);
        menuLogOut.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, LoginActivity.class));
                Toast.makeText(ManagerActivity.this, "Successful log out", Toast.LENGTH_SHORT).show();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
