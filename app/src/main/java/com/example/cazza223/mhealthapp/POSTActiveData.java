package com.example.cazza223.mhealthapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cazza223 on 10/30/2016.
 */

public class POSTActiveData extends StringRequest {
    private static final String SUBMIT_REST_URL = "https://sjumhealthapp.tk:5000/submit-data-acc";
    private Map<String, String> params;

    public POSTActiveData(String disease, String BS_ACC, String HR_ACC, String systolic_ACC, String diastolic_ACC, String is_baseline,
                           String accel_x, String accel_y, String accel_z, Response.Listener<String> listener,
                           Response.ErrorListener errorListener){

        super(Method.POST, SUBMIT_REST_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("disease", disease);
        params.put("BS_ACC", BS_ACC);
        params.put("HR_ACC", HR_ACC);
        params.put("systolic_ACC", systolic_ACC);
        params.put("diastolic_ACC", diastolic_ACC);
        params.put("is_baseline", is_baseline);
        params.put("accel_x", accel_x);
        params.put("accel_y", accel_y);
        params.put("accel_z", accel_z);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
