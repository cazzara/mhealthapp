package com.example.cazza223.mhealthapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cazza223 on 10/23/2016.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_URL = "https://sjumhealthapp.tk:5000/register";
    private Map<String, String> params;

    public RegisterRequest(String username, String password, String first, String last, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST, REGISTER_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("fname", first);
        params.put("lname", last);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
