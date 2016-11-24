package com.example.cazza223.mhealthapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class AtRestDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_rest_data);
        Intent intent = getIntent();

        final String is_baseline = intent.getStringExtra("is_baseline");
        final String disease = intent.getStringExtra("disease");
        final Button bSubmit = (Button)findViewById(R.id.bSubmitResting);

        final EditText et_BS_AR = (EditText)findViewById(R.id.et_BS_AR);
        final EditText et_HR_AR = (EditText)findViewById(R.id.et_HR_AR);
        final EditText et_systolic_AR = (EditText)findViewById(R.id.et_systolic_AR);
        final EditText et_diastolic_AR = (EditText)findViewById(R.id.et_diastolic_AR);
        final EditText et_accel_x = (EditText)findViewById(R.id.et_accel_x);
        final EditText et_accel_y = (EditText)findViewById(R.id.et_accel_y);
        final EditText et_accel_z = (EditText)findViewById(R.id.et_accel_z);
        final TextView AR_message = (TextView)findViewById(R.id.tv_restMessage);

        String message = "Enter Resting Data for " + disease;

        AR_message.setText(message);

        bSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String BS_AR = et_BS_AR.getText().toString();
                String HR_AR = et_HR_AR.getText().toString();
                String systolic_AR = et_systolic_AR.getText().toString();
                String diastolic_AR = et_diastolic_AR.getText().toString();
                String accel_x = et_accel_x.getText().toString();
                String accel_y = et_accel_y.getText().toString();
                String accel_z = et_accel_z.getText().toString();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Intent intent1 = new Intent(AtRestDataActivity.this, user_area.class);
                                AtRestDataActivity.this.startActivity(intent1);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(AtRestDataActivity.this);
                                builder.setMessage("Submit Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException err){
                            System.err.println(err);
                        }
                    }
                };

                POSTRestingData POST = new POSTRestingData(disease, BS_AR, HR_AR, systolic_AR,
                        diastolic_AR, is_baseline, accel_x, accel_y, accel_z, listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println(error);
                    }
                });

                VolleyProvider.getInstance(AtRestDataActivity.this).addRequest(POST);
            }
        });
    }
}


