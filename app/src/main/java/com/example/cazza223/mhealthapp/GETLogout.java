package com.example.cazza223.mhealthapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by cazza223 on 11/3/2016.
 */

public class GETLogout extends StringRequest {
    private static final String LOGOUT_URL = "https://sjumhealthapp.tk:5000/logout";

    public GETLogout(Response.Listener<String> listener,
                          Response.ErrorListener errorListener){

        super(Method.GET, LOGOUT_URL, listener, errorListener);


    }
}
