package com.example.cazza223.mhealthapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class drug_survey extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_survey);

        final TextView tv_Q1 = (TextView)findViewById(R.id.tv_Q1);
        final TextView tv_Q2 = (TextView)findViewById(R.id.tv_Q2);
        final TextView tv_Q3 = (TextView)findViewById(R.id.tv_Q3);
        final TextView tv_Q4 = (TextView)findViewById(R.id.tv_Q4);
        final TextView tv_Q5 = (TextView)findViewById(R.id.tv_Q5);

        final SeekBar sb_Q1 = (SeekBar)findViewById(R.id.sb_Q1);
        final SeekBar sb_Q2 = (SeekBar)findViewById(R.id.sb_Q2);
        final SeekBar sb_Q3 = (SeekBar)findViewById(R.id.sb_Q3);
        final SeekBar sb_Q4 = (SeekBar)findViewById(R.id.sb_Q4);
        final SeekBar sb_Q5 = (SeekBar)findViewById(R.id.sb_Q5);

        final Button submit = (Button)findViewById(R.id.bSubmit_DS);


        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int Q1 = sb_Q1.getProgress();
                int Q2 = sb_Q2.getProgress();
                int Q3 = sb_Q3.getProgress();
                int Q4 = sb_Q4.getProgress();
                int Q5 = sb_Q5.getProgress();
                String is_baseline = getIntent().getStringExtra("is_baseline");
                String results = Q1 + " "+ Q2 + " " + Q3 + " "+ Q4 + " "+ Q5;

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(drug_survey.this, user_area.class);
                                drug_survey.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(drug_survey.this);
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

                POST_DEQ POST = new POST_DEQ(results, is_baseline, listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println(error);
                    }
                });

                VolleyProvider.getInstance(drug_survey.this).addRequest(POST);

            }
        });

    }
}
