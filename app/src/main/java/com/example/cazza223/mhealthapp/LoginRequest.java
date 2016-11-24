package com.example.cazza223.mhealthapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cazza223 on 10/29/2016.
 */

public class LoginRequest extends StringRequest {
    private static final String LOGIN_URL = "https://sjumhealthapp.tk:5000/login";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST, LOGIN_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
