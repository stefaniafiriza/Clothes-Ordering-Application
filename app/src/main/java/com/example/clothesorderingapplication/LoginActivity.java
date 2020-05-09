package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    protected EditText Email;
    protected EditText Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login =  findViewById(R.id.btn_login);
        Email =  findViewById(R.id.email);
        Password =  findViewById(R.id.password);
        Register = findViewById(R.id.sendToSignUp);
        final API api = new API(this);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                api.login(
                        Email.getText().toString(),
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
                                // go to next activity

                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            }

                            @Override
                            public void onError(VolleyError error, Context context) {
                                // error
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
