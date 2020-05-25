package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    protected TextView Login;
    protected EditText ManagerCode;
    protected Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Login = findViewById(R.id.sendToLogin);
        ManagerCode = findViewById(R.id.managercode);
        aSwitch = findViewById(R.id.switch1);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(indent);
            }
        });

        ManagerCode.setVisibility(View.INVISIBLE);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagerCode.setVisibility(View.VISIBLE);
                aSwitch.setVisibility(View.INVISIBLE);
            }
        });
    }
}
