package com.example.clothesorderingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
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
    protected ImageView i1, i2, i3, i4, i5, i6;
    protected ImageButton add1, add2, add3, add4, add5, add6;
    protected TextView p1, p2, p3, p4, p5, p6;
    protected ArrayList<ImageButton> addButton = new ArrayList<>();
    protected ArrayList<TextView> price = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        News = findViewById(R.id.btn1);
        Accessory = findViewById(R.id.btn2);
        Sale = findViewById(R.id.btn4);
        search = findViewById(R.id.search);
        i1 = findViewById(R.id.search_1);
        i2 = findViewById(R.id.search_2);
        i3 = findViewById(R.id.search_3);
        i4 = findViewById(R.id.search_4);
        i5 = findViewById(R.id.search_5);
        i6 = findViewById(R.id.search_6);
        add1 = findViewById(R.id.search_add1);
        add2 = findViewById(R.id.search_add2);
        add3 = findViewById(R.id.search_add3);
        add4 = findViewById(R.id.search_add4);
        add5 = findViewById(R.id.search_add5);
        add6 = findViewById(R.id.search_add6);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        p3 = findViewById(R.id.p3);
        p4 = findViewById(R.id.p4);
        p5 = findViewById(R.id.p5);
        p6 = findViewById(R.id.p6);
        cancel = new ProgressDialog(this);

        List<ImageButton> allButton = Arrays.asList(add1, add2, add3, add4, add5, add6);
        List<TextView> allprice = Arrays.asList(p1, p2, p3, p4, p5, p6);
        addButton.addAll(allButton);
        price.addAll(allprice);

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

        for (ImageButton current : addButton) {
            current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Added to shopping basket", Toast.LENGTH_LONG).show();
                }
            });
        }

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                api.search(
                        search.getQuery().toString(),
                        new ICallback() {
                            @Override
                            public void onFinish(String response, Context context) {

                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Product p = new Product();
                                        p.fromJSONObject(jsonObject);

                                        setContentView(R.layout.search_item);
                                        if (p.getName().equals("dress")) {
                                            i1.setImageResource(R.drawable.r1);
                                            i2.setImageResource(R.drawable.r2);
                                            i3.setImageResource(R.drawable.r3);
                                            i4.setImageResource(R.drawable.r4);
                                            i5.setImageResource(R.drawable.r5);
                                            i6.setVisibility(View.INVISIBLE);
                                            add6.setVisibility(View.INVISIBLE);
                                            for (TextView textView : price) {
                                                textView.setText(p.getPrice());
                                            }
                                            p6.setVisibility(View.INVISIBLE);
                                        }
                                        if (p.getName().equals("jeans")) {
                                            i1.setImageResource(R.drawable.b1);
                                            i2.setImageResource(R.drawable.b2);
                                            i3.setImageResource(R.drawable.b3);
                                            i4.setImageResource(R.drawable.b4);
                                            i5.setImageResource(R.drawable.b5);
                                            i6.setImageResource(R.drawable.b6);
                                            for (TextView textView : price) {
                                                textView.setText(p.getPrice());
                                            }
                                        }
                                        if (p.getName().equals("skirt")) {
                                            i1.setImageResource(R.drawable.f1);
                                            i2.setImageResource(R.drawable.f2);
                                            i3.setImageResource(R.drawable.f3);
                                            i4.setImageResource(R.drawable.f4);
                                            i5.setImageResource(R.drawable.f5);
                                            i6.setVisibility(View.INVISIBLE);
                                            add6.setVisibility(View.INVISIBLE);
                                            for (TextView textView : price) {
                                                textView.setText(p.getPrice());
                                            }
                                            p6.setVisibility(View.INVISIBLE);
                                        }
                                        if (p.getName().equals("t-shirt")) {
                                            i1.setImageResource(R.drawable.t1);
                                            i2.setImageResource(R.drawable.t2);
                                            i3.setImageResource(R.drawable.t3);
                                            i4.setImageResource(R.drawable.t4);
                                            i5.setImageResource(R.drawable.t5);
                                            i6.setImageResource(R.drawable.t6);
                                            for (TextView textView : price) {
                                                textView.setText(p.getPrice());
                                            }
                                        }
                                        if (p.getName().equals("belt")) {
                                            i1.setImageResource(R.drawable.c1);
                                            i2.setImageResource(R.drawable.c2);
                                            i3.setImageResource(R.drawable.c3);
                                            i4.setImageResource(R.drawable.c4);
                                            i5.setImageResource(R.drawable.c5);
                                            i6.setImageResource(R.drawable.c6);
                                            for (TextView textView : price) {
                                                textView.setText(p.getPrice());
                                            }
                                        }
                                        if (p.getName().equals("bag")) {
                                            i1.setImageResource(R.drawable.g1);
                                            i2.setImageResource(R.drawable.g2);
                                            i3.setImageResource(R.drawable.g3);
                                            i4.setImageResource(R.drawable.g4);
                                            i5.setImageResource(R.drawable.g5);
                                            i6.setImageResource(R.drawable.g6);
                                        }
                                        if (p.getName().equals("scarf")) {
                                            i1.setImageResource(R.drawable.e1);
                                            i2.setImageResource(R.drawable.e2);
                                            i3.setImageResource(R.drawable.e3);
                                            i4.setImageResource(R.drawable.e4);
                                            i5.setVisibility(View.INVISIBLE);
                                            i6.setVisibility(View.INVISIBLE);
                                            add5.setVisibility(View.INVISIBLE);
                                            add6.setVisibility(View.INVISIBLE);
                                            for (TextView textView : price) {
                                                textView.setText(p.getPrice());
                                            }
                                            p5.setVisibility(View.INVISIBLE);
                                            p6.setVisibility(View.INVISIBLE);
                                        }
                                        if (p.getName().equals("blouse")) {
                                            i1.setImageResource(R.drawable.bl1);
                                            i2.setImageResource(R.drawable.bl2);
                                            i3.setImageResource(R.drawable.bl3);
                                            i4.setImageResource(R.drawable.bl4);
                                            i5.setImageResource(R.drawable.bl5);
                                            i6.setImageResource(R.drawable.bl6);
                                            for (TextView textView : price) {
                                                textView.setText(p.getPrice());
                                            }
                                        }
                                        if (p.getName().equals("jaket")) {
                                            i1.setImageResource(R.drawable.gh1);
                                            i2.setImageResource(R.drawable.gh2);
                                            i3.setImageResource(R.drawable.gh3);
                                            i4.setImageResource(R.drawable.gh4);
                                            i5.setVisibility(View.INVISIBLE);
                                            i6.setVisibility(View.INVISIBLE);
                                            add5.setVisibility(View.INVISIBLE);
                                            add6.setVisibility(View.INVISIBLE);
                                            for (TextView textView : price) {
                                                textView.setText(p.getPrice());
                                            }
                                            p5.setVisibility(View.INVISIBLE);
                                            p6.setVisibility(View.INVISIBLE);
                                        }
                                        if (p.getName().equals("coat")) {
                                            i1.setImageResource(R.drawable.p1);
                                            i2.setImageResource(R.drawable.p2);
                                            i3.setImageResource(R.drawable.p3);
                                            i4.setImageResource(R.drawable.p4);
                                            i5.setImageResource(R.drawable.p5);
                                            i6.setImageResource(R.drawable.p6);
                                            for (TextView textView : price) {
                                                textView.setText(p.getPrice());
                                            }
                                        }
                                    }

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
