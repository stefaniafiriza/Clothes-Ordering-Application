package com.example.clothesorderingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaleActivity extends AppCompatActivity {

    protected MenuItem menuItem_basket, menuItem_favorite,menuLogOut;
    protected TextView badgeCounter;
    protected int basketItems = 0;
    protected int favoriteItems = 3;
    protected ImageButton add1, add2, add3, add4, add5, add6, add7, add8, add9, add10, add11, add12, add13, add14, add15, add16;
    protected ArrayList<ImageButton> addButton = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        add1 = findViewById(R.id.add39);
        add2 = findViewById(R.id.add40);
        add3 = findViewById(R.id.add41);
        add4 = findViewById(R.id.add42);
        add5 = findViewById(R.id.add43);
        add6 = findViewById(R.id.add44);
        add7 = findViewById(R.id.add45);
        add8 = findViewById(R.id.add46);
        add9 = findViewById(R.id.add48);
        add10 = findViewById(R.id.add49);
        add11 = findViewById(R.id.add50);
        add12 = findViewById(R.id.add51);
        add13 = findViewById(R.id.add52);
        add14 = findViewById(R.id.add53);
        add15 = findViewById(R.id.add54);
        add16 = findViewById(R.id.add55);

        List<ImageButton> allButton = Arrays.asList(add1, add2, add3, add4, add5, add6, add7, add8, add9, add10, add11, add12, add13, add14, add15, add16);
        addButton.addAll(allButton);

        for (ImageButton current : addButton) {
            current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Added to shopping basket", Toast.LENGTH_LONG).show();
                }
            });
        }
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
                startActivity(new Intent(SaleActivity.this, ShopBasketActivity.class));
                return true;
            case R.id.log_out:
                startActivity(new Intent(SaleActivity.this, LoginActivity.class));
                Toast.makeText(SaleActivity.this, "Successful log out", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
