package com.example.clothesorderingapplication.api.interfaces;

import android.content.Context;

import com.android.volley.VolleyError;

public interface ICallback {
    public void onFinish(String response, Context context);
    public void onError(VolleyError error, Context context);
}
