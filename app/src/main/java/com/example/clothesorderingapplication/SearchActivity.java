package com.example.clothesorderingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.Utils;
import com.example.clothesorderingapplication.api.interfaces.ICallback;
import com.example.clothesorderingapplication.data.Product;
import com.example.clothesorderingapplication.data.SearchAdapter;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    protected MenuItem menuItem_basket, menuItem_favorite, menuLogOut;
    protected TextView badgeCounter;
    protected int basketItems = 0;
    protected int favoriteItems = 3;
    protected Button News;
    protected Button Accessory;
    protected Button Sale;
    protected SearchView search;
    protected ProgressDialog cancel;
    protected ArrayList<ImageButton> addButton = new ArrayList<>();
    protected ArrayList<TextView> price = new ArrayList<>();
    protected LinkedList<Product> searchedProducts = new LinkedList<>();
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected SearchAdapter searchAdapter = new SearchAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        News = findViewById(R.id.btn1);
        Accessory = findViewById(R.id.btn2);
        Sale = findViewById(R.id.btn4);
        search = findViewById(R.id.search);
        cancel = new ProgressDialog(this);

        final API api = new API(this);

        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, NewsActivity.class));
            }
        });

        Accessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, AccessoryActivity.class));
            }
        });

        Sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, SaleActivity.class));
            }
        });

        layoutManager = new LinearLayoutManager(this);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setContentView(R.layout.search_item);

                recyclerView = findViewById(R.id.search_list);
                recyclerView.setHasFixedSize(false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(searchAdapter);

                api.search(
                        search.getQuery().toString(),
                        new ICallback() {
                            @Override
                            public void onFinish(String response, Context context) {

                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        searchedProducts.add(Product.fromJSONObject(jsonObject));
                                    }
                                    searchAdapter.setData(searchedProducts);
                                    searchAdapter.notifyDataSetChanged();

                                } catch (Exception ignored) {
                                    cancel = new ProgressDialog(SearchActivity.this);
                                    cancel.setMessage("The product you are looking for does not exist!");
                                    cancel.setCancelable(false);
                                    cancel.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            cancel.dismiss();
                                        }
                                    });
                                    cancel.show();
                                }

                            }

                            @Override
                            public void onError(VolleyError error, Context context) {

                            }
                        }
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
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
                startActivity(new Intent(SearchActivity.this, ShopBasketActivity.class));
                return true;
            case R.id.log_out:
                startActivity(new Intent(SearchActivity.this, LoginActivity.class));
                Toast.makeText(SearchActivity.this, "Successful log out", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
