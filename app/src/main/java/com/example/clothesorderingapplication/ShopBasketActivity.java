package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShopBasketActivity extends AppCompatActivity {

    protected Button Back;
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected Button order, orderStatus;
    protected TextView empty_cart, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_basket);

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

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopBasketActivity.this, HomeActivity.class));
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShopBasketActivity.this, "The order successfully registered", Toast.LENGTH_SHORT).show();
                order.setVisibility(View.INVISIBLE);
                orderStatus.setVisibility(View.VISIBLE);
            }
        });

        orderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopBasketActivity.this,OrderStatusActivity.class));
            }
        });
    }
}
