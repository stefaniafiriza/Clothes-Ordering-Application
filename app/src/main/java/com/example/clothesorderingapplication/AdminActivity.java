package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    protected MenuItem menuLogOut,settings;
    protected Button user, manager,admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        user = findViewById(R.id.user);
        manager = findViewById(R.id.manager);
        admin = findViewById(R.id.admin);

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

        return super.onCreateOptionsMenu(menu);
    }
}
