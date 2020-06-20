package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.Utils;
import com.example.clothesorderingapplication.api.interfaces.ICallback;

import org.json.JSONObject;

public class RegistrationAdminActivity extends AppCompatActivity {

    protected TextView login;
    protected Button register;
    protected ProgressDialog loadingBar;
    protected EditText Name;
    protected EditText phoneNumber;
    protected EditText emailAddress;
    protected EditText user;
    protected EditText password;
    protected EditText admincode;
    protected Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_admin);

        login = findViewById(R.id.admin_sendToLogin);
        register = findViewById(R.id.admin_btn_sign_up);
        Name = findViewById(R.id.admin_name);
        phoneNumber = findViewById(R.id.admin_phoneNumber);
        emailAddress = findViewById(R.id.admin_mail);
        user = findViewById(R.id.admin_username);
        password = findViewById(R.id.admin_generatePassword);
        loadingBar = new ProgressDialog(this);
        admincode = findViewById(R.id.admincode);
        aSwitch = findViewById(R.id.switch2);

        final API api = new API(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationAdminActivity.this, LoginActivity.class));
            }
        });

        admincode.setVisibility(View.INVISIBLE);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admincode.setVisibility(View.VISIBLE);
                aSwitch.setVisibility(View.INVISIBLE);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().isEmpty() || password.getText().toString().isEmpty() || Name.getText().toString().isEmpty() || emailAddress.getText().toString().isEmpty() || phoneNumber.getText().toString().isEmpty()) {
                    Toast.makeText(RegistrationAdminActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("Create Account");
                    loadingBar.setMessage("Please wait.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    api.register(
                            user.getText().toString(),
                            password.getText().toString(),
                            Name.getText().toString(),
                            emailAddress.getText().toString(),
                            phoneNumber.getText().toString(),
                            admincode.getText().toString(),
                            new ICallback() {
                                @Override
                                public void onFinish(String response, Context context) {
                                    JSONObject resp = Utils.responseToJSON(response);
                                    try {
                                        String type = resp.getJSONObject("result").getString("type");
                                        if (type.equals("error")) {
                                            // error
                                            loadingBar.dismiss();
                                        } else {
                                            // successful
                                            Toast.makeText(RegistrationAdminActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();

                                            startActivity(new Intent(RegistrationAdminActivity.this, LoginActivity.class));
                                        }

                                    } catch (Exception ignored) {
                                        // error
                                    }

                                }

                                @Override
                                public void onError(VolleyError error, Context context) {

                                }
                            }
                    );
                }

            }
        });
    }
}
