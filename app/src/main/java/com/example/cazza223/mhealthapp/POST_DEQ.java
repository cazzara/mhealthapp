package com.example.cazza223.mhealthapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cazza223 on 11/19/2016.
 */

public class POST_DEQ extends StringRequest{
    private static final String SUBMIT_REST_URL = "https://sjumhealthapp.tk:5000/submit-data-deq";
    private Map<String, String> params;

    public POST_DEQ(String results,String is_baseline, Response.Listener<String> listener, Response.ErrorListener errorListener){

        super(Request.Method.POST, SUBMIT_REST_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("results", results);
        params.put("is_baseline", is_baseline);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
