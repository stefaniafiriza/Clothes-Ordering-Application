package com.example.clothesorderingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.interfaces.ICallback;
import com.example.clothesorderingapplication.data.Product;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;

public class HomeActivity extends AppCompatActivity {

    protected MenuItem menuItem_basket, menuItem_favorite, menuLogOut;
    protected TextView badgeCounter;
    protected int basketItems = 0;
    protected int favoriteItems = 3;
    protected ProgressDialog loadingBar;
    protected Button Products;
    protected Button News;
    protected Button Accessory;
    protected Button Sale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Products = findViewById(R.id.products);
        News = findViewById(R.id.for_news);
        Accessory = findViewById(R.id.for_accessory);
        Sale = findViewById(R.id.for_sale);

        Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });

        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, NewsActivity.class));
            }
        });

        Accessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AccessoryActivity.class));
            }
        });

        Sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SaleActivity.class));
            }
        });


        // get the products from the database
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
                    Product.products = new LinkedList<>();
                    for(int i =0; i < jsonArray.length(); i ++){
                        Product.products.add(Product.fromJSONObject(jsonArray.getJSONObject(i)));
                    }
                    loadingBar.dismiss();
                    return;
                } catch (JSONException e) {
                    loadingBar.dismiss();
                    e.printStackTrace();
                }

                Toast.makeText(HomeActivity.this,"Failed to retrieve products from the server.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VolleyError error, Context context) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.basket_menu, menu);

        menuItem_basket = menu.findItem(R.id.notification);
        menuItem_favorite = menu.findItem(R.id.favorite);
        menuLogOut = menu.findItem(R.id.log_out);

        if (basketItems == 0) {
            menuItem_basket.setActionView(null);
        } else {
            menuItem_basket.setActionView(R.layout.notification_badge);
            View view = menuItem_basket.getActionView();
            badgeCounter = view.findViewById(R.id.badge_counter);
            badgeCounter.setText(String.valueOf(basketItems));
            menuItem_basket.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOptionsItemSelected(menuItem_basket);
                }
            });
        }

        if (favoriteItems == 0) {
            menuItem_favorite.setActionView(null);
        } else {
            menuItem_favorite.setActionView(R.layout.notification_favorite);
            View view = menuItem_favorite.getActionView();
            badgeCounter = view.findViewById(R.id.badge_counter);
            badgeCounter.setText(String.valueOf(favoriteItems));
        }

        menuLogOut.setActionView(R.layout.log_out);
        menuLogOut.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuLogOut);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                startActivity(new Intent(HomeActivity.this, ShopBasketActivity.class));
                return true;
            case R.id.log_out:
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                Toast.makeText(HomeActivity.this,"Successful log out", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
