package com.example.clothesorderingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.clothesorderingapplication.api.API;
import com.example.clothesorderingapplication.api.Utils;
import com.example.clothesorderingapplication.api.interfaces.ICallback;
import com.example.clothesorderingapplication.data.User;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacebookActivity extends AppCompatActivity {

    protected TextView name;
    protected CircleImageView profileImage;
    protected LoginButton facebookLogin;
    protected CallbackManager callbackManager;
    protected Button pageHome;
    protected static final String TAG = "FaceboockAuthentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        name = findViewById(R.id.info);
        profileImage = findViewById(R.id.profile);
        facebookLogin = findViewById(R.id.login_button);
        pageHome = findViewById(R.id.pageHome);

        name.setText("Your name");
        profileImage.setImageResource(R.drawable.profile);
        pageHome.setVisibility(View.INVISIBLE);

        callbackManager = CallbackManager.Factory.create();
        facebookLogin.setPermissions(Arrays.asList("email", "public_profile"));
        checkLoginStatus();

        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess" + loginResult);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError" + error);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                name.setText("Your name");
                profileImage.setImageResource(R.drawable.profile);
                pageHome.setVisibility(View.INVISIBLE);
                Toast.makeText(FacebookActivity.this, "User Logged out", Toast.LENGTH_LONG).show();
            } else {
                pageHome.setVisibility(View.VISIBLE);
                loadUserProfile(currentAccessToken);
                pageHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(FacebookActivity.this,HomeActivity.class));
                    }
                });
            }
        }
    };

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String id = object.getString("id");

                    String email = object.getString("email");
                    String name = object.getString("name");
                    final API api = new API(null);

                    // if the user is not registered with facebook on the server
                    // it will automatically register
                    api.facebookLogin(email, id, name, new ICallback() {
                        @Override
                        public void onFinish(String response, Context context) {
                            JSONObject resp = Utils.responseToJSON(response);
                            User user = User.fromJSONObject(resp);
                        }

                        @Override
                        public void onError(VolleyError error, Context context) {

                        }
                    });

                    String imageURL = "https://graph.facebook.com/" + id + "/picture?type=normal";
                    name.setText(first_name + " " + last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(FacebookActivity.this).load(imageURL).into(profileImage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null){
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }
}
