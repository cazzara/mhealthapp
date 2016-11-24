package com.example.cazza223.mhealthapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


public class user_area extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final Spinner spinner = (Spinner) findViewById(R.id.selection_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.diseases, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final Button bRestData = (Button) findViewById(R.id.bRestData);
        final Button bActiveData = (Button) findViewById(R.id.bActiveData);
        final Button bLogout = (Button) findViewById(R.id.bLogout);
        final CheckBox baseline = (CheckBox) findViewById(R.id.cb_baseline);
        final TextView welcome = (TextView) findViewById(R.id.welcome);
        final Button bDrugSurvey = (Button) findViewById(R.id.bDrugSurvey);
        final TextView drugSurvey = (TextView) findViewById(R.id.tv_DEQ);
        String user = this.getIntent().getStringExtra("username");

        String message = "Welcome " + user;
        welcome.setText(message);





        bRestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restDataIntent = new Intent(v.getContext(), AtRestDataActivity.class);
                if(baseline.isChecked())
                    restDataIntent.putExtra("is_baseline", "True");
                else
                    restDataIntent.putExtra("is_baseline", "False");
                restDataIntent.putExtra("disease", spinner.getSelectedItem().toString());
                user_area.this.startActivity(restDataIntent);

            }
        });

        bActiveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activeDataIntent = new Intent(v.getContext(), ActiveDataActivity.class);
                if(baseline.isChecked())
                    activeDataIntent.putExtra("is_baseline", "True");
                else
                    activeDataIntent.putExtra("is_baseline", "False");
                activeDataIntent.putExtra("disease", spinner.getSelectedItem().toString());
                user_area.this.startActivity(activeDataIntent);
            }
        });

        bDrugSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DEQIntent = new Intent(v.getContext(), drug_survey.class);
                if(baseline.isChecked())
                    DEQIntent.putExtra("is_baseline", "True");
                else
                    DEQIntent.putExtra("is_baseline", "False");
                user_area.this.startActivity(DEQIntent);

            }
        });

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(user_area.this, LoginActivity.class);
                                user_area.this.startActivity(intent);
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(user_area.this);
                                builder.setMessage("Logout Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                GETLogout getLogout = new GETLogout(listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });

                VolleyProvider.getInstance(user_area.this).addRequest(getLogout);

            }
        });
    }
}
