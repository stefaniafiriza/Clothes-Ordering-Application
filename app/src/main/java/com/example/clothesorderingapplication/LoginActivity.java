package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    protected TextView Register;
    protected Button Login;
    protected EditText Username;
    protected EditText Password;
    protected Switch aSwitch;
    protected EditText Manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = findViewById(R.id.btn_login);
        Username = findViewById(R.id.use);
        Password = findViewById(R.id.password);
        Register = findViewById(R.id.sendToSignUp);
        aSwitch = findViewById(R.id.switch2);
        Manager = findViewById(R.id.code);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(LoginActivity.this, ManagerActivity.class));
                        }
                    });
                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        Manager.setVisibility(View.INVISIBLE);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manager.setVisibility(View.VISIBLE);
                aSwitch.setVisibility(View.INVISIBLE);
            }
        });
    }
}
