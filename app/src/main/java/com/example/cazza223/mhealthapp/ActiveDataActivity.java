package com.example.cazza223.mhealthapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class ActiveDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_data);
        Intent intent = getIntent();

        final String is_baseline = intent.getStringExtra("is_baseline");
        final String disease = intent.getStringExtra("disease");

        final Button bSubmit = (Button)findViewById(R.id.bSubmitActive);


        final EditText et_BS_ACC = (EditText)findViewById(R.id.et_BS_ACC);
        final EditText et_HR_ACC = (EditText)findViewById(R.id.et_HR_ACC);
        final EditText et_systolic_ACC = (EditText)findViewById(R.id.et_systolic_ACC);
        final EditText et_diastolic_ACC = (EditText)findViewById(R.id.et_diastolic_ACC);
        final EditText et_accel_x = (EditText)findViewById(R.id.et_accel_x);
        final EditText et_accel_y = (EditText)findViewById(R.id.et_accel_y);
        final EditText et_accel_z = (EditText)findViewById(R.id.et_accel_z);
        final TextView AC_message = (TextView)findViewById(R.id.tv_activeMessage);

        String message = "Enter Resting Data for " + disease;
        AC_message.setText(message);

        bSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String BS_ACC = et_BS_ACC.getText().toString();
                String HR_ACC = et_HR_ACC.getText().toString();
                String systolic_ACC = et_systolic_ACC.getText().toString();
                String diastolic_ACC = et_diastolic_ACC.getText().toString();
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
                                Intent intent1 = new Intent(ActiveDataActivity.this, user_area.class);
                                ActiveDataActivity.this.startActivity(intent1);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(ActiveDataActivity.this);
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

                POSTActiveData POST = new POSTActiveData(disease, BS_ACC, HR_ACC, systolic_ACC,
                        diastolic_ACC, is_baseline, accel_x, accel_y, accel_z, listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println(error);
                    }
                });
                System.out.println(POST.getParams().toString());
                VolleyProvider.getInstance(ActiveDataActivity.this).addRequest(POST);

            }
        });


    }
}
