package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.Utils;
import com.example.clothesorderingapplication.api.interfaces.ICallback;
import com.example.clothesorderingapplication.data.User;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    protected TextView Register;
    protected Button Login;
    protected EditText Username;
    protected EditText Password;
    protected TextView Facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = findViewById(R.id.btn_login);
        Username = findViewById(R.id.use);
        Password = findViewById(R.id.password);
        Register = findViewById(R.id.sendToSignUp);

        final API api = new API(this);

        Facebook = findViewById(R.id.facebook_login);

        Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, FacebookActivity.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                api.login(
                        Username.getText().toString(),
                        Password.getText().toString(),
                        new ICallback() {
                            @Override
                            public void onFinish(String response, Context context) {
                                JSONObject resp = Utils.responseToJSON(response);
                                try{
                                    String errorMessage = resp.getJSONObject("result").getString("message");
                                    // error;
                                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();

                                }catch(Exception e){
                                    // successful, if the response does not contain "result" and "message" it means it's the user string.
                                }
                                User user = User.fromJSONObject(resp);
                                User.logged_in_user = user;
                                // go to next activity
                                if(!user.getManagerCode().equals("")) {
                                    startActivity(new Intent(LoginActivity.this, ManagerActivity.class));
                                }else{
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                }

                            }

                            @Override
                            public void onError(VolleyError error, Context context) {
                                Toast.makeText(getApplicationContext(), "Cannot connect to the server.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );


            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }
}
