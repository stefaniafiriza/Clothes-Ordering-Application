package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.Utils;
import com.example.clothesorderingapplication.api.interfaces.ICallback;

import org.json.JSONObject;

public class AdminActivity extends AppCompatActivity {

    protected MenuItem menuLogOut,settings;
    protected Button user, manager,admin;
    protected Button toggleAddToCart;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        user = findViewById(R.id.user);
        manager = findViewById(R.id.manager);
        admin = findViewById(R.id.admin);
        api = new API(this);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,RegistrationActivity.class));
            }
        });

        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,RegistrationActivity.class));
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,RegistrationAdminActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.admin_menu, menu);
        menuLogOut = menu.findItem(R.id.log_out);
        settings = menu.findItem(R.id.settings);

        menuLogOut.setActionView(R.layout.log_out);
        menuLogOut.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                Toast.makeText(AdminActivity.this, "Successful log out", Toast.LENGTH_SHORT).show();
            }
        });

        settings.setActionView(R.layout.settings);

        settings.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.admin_settings);
                toggleAddToCart = findViewById(R.id.toggle_add_to_cart);
                toggleAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    api.toggleAddToCart(new ICallback() {
                        @Override
                        public void onFinish(String response, Context context) {
                            if(response.contains("error")){
                                Toast.makeText(AdminActivity.this, "Ops, looks like something went wrong.", Toast.LENGTH_SHORT).show();
                            }

                            JSONObject resp = Utils.responseToJSON(response);
                                try{
                                String message = resp.getJSONObject("result").getString("message");
                                // error;
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            }catch(Exception e){
                                    Toast.makeText(AdminActivity.this, "Ops, looks like something went wrong.", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onError(VolleyError error, Context context) {

                        }
                    });
                    }
                });
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
