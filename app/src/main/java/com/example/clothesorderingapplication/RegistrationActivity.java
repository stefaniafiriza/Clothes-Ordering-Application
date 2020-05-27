package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.Utils;
import com.example.clothesorderingapplication.api.interfaces.ICallback;

import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {

    protected TextView Login;
    protected EditText ManagerCode;
    protected Switch aSwitch;
    protected Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Login = findViewById(R.id.sendToLogin);
        ManagerCode = findViewById(R.id.managercode);
        aSwitch = findViewById(R.id.switch1);
        Register = findViewById(R.id.btn_sign_up);

        final API api = new API(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.register(
                        ((EditText) findViewById(R.id.username)).getText().toString(),
                        ((EditText) findViewById(R.id.generatePassword)).getText().toString(),
                        ((EditText) findViewById(R.id.Name)).getText().toString(),
                        ((EditText) findViewById(R.id.mail)).getText().toString(),
                        ((EditText) findViewById(R.id.phoneNumber)).getText().toString(),
                        ManagerCode.getText().toString(),
                        new ICallback() {
                            @Override
                            public void onFinish(String response, Context context) {
                                JSONObject resp = Utils.responseToJSON(response);
                                try{
                                    String type = resp.getJSONObject("result").getString("type");
                                    if(type.equals("error")){
                                        // error
                                        return;
                                    }
                                    else{
                                        // successful
                                        return;
                                    }

                                }catch (Exception ignored) {
                                    // error
                                }

                            }

                            @Override
                            public void onError(VolleyError error, Context context) {

                            }
                        }
                );
            }
        });


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
