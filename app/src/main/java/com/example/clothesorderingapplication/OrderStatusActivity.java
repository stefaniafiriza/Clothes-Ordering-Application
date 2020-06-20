package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.interfaces.ICallback;
import com.example.clothesorderingapplication.data.User;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderStatusActivity extends AppCompatActivity {

    protected Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        final API api = new API(this);


        api.getOrderByUserID(User.logged_in_user.getId() + "", new ICallback() {
            @Override
            public void onFinish(String response, Context context) {
                if(response.contains("error")){
                    Toast.makeText(OrderStatusActivity.this, "Something went wrong and the order could not load.", Toast.LENGTH_SHORT).show();
                }else{
                    response = response.substring(response.indexOf('[') + 2, response.indexOf(']') - 1);
                    response = response.replaceAll("\\{", "[");
                    response = response.replaceAll("\\}", "]");
                    response = "{" + response + "}";

                    try {
                        JSONObject json = new JSONObject(response);
                        String status = json.getString("Status");
                        TextView tv = findViewById(R.id.livrare);
                        if(status.equals("0")){
                            tv.setText(R.string.approve_order);
                        }else if(status.equals("1")){
                            tv.setText(R.string.in_2_3_working_days_the_courier_will_contact_you);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(VolleyError error, Context context) {

            }
        });

        back=findViewById(R.id.back_home);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderStatusActivity.this, HomeActivity.class));
            }
        });
    }
}
