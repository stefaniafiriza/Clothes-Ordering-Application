package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    protected MenuItem menuItem_basket, menuItem_favorite;
    protected TextView badgeCounter;
    protected int basketItems = 0;
    protected int favoriteItems = 3;
    protected Button Products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Products=findViewById(R.id.products);

        Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SearchActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.basket_menu, menu);

        menuItem_basket = menu.findItem(R.id.notification);
        menuItem_favorite = menu.findItem(R.id.favorite);

        if (basketItems == 0) {
            menuItem_basket.setActionView(null);
        } else {
            menuItem_basket.setActionView(R.layout.notification_badge);
            View view = menuItem_basket.getActionView();
            badgeCounter = view.findViewById(R.id.badge_counter);
            badgeCounter.setText(String.valueOf(basketItems));
        }

        if (favoriteItems == 0) {
            menuItem_favorite.setActionView(null);
        } else {
            menuItem_favorite.setActionView(R.layout.notification_favorite);
            View view = menuItem_favorite.getActionView();
            badgeCounter = view.findViewById(R.id.badge_counter);
            badgeCounter.setText(String.valueOf(favoriteItems));
        }

        return super.onCreateOptionsMenu(menu);
    }
}