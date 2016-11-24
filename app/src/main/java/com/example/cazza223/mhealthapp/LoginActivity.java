package com.example.cazza223.mhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText et_username = (EditText) findViewById(R.id.et_username);
        final EditText et_passwd = (EditText) findViewById(R.id.et_passw);
        final Button bLogin = (Button) findViewById(R.id.bLogin);

        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String username = et_username.getText().toString();
                final String password = et_passwd.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(LoginActivity.this, user_area.class);
                                intent.putExtra("username", username);
                                LoginActivity.this.startActivity(intent);
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(username, password, responseListener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.print(error);
                    }
                });

                VolleyProvider.getInstance(LoginActivity.this).addRequest(loginRequest);
            }
        });

        final TextView registerLink = (TextView) findViewById(R.id.tv_registerHere);

        registerLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
             Intent registerIntent = new Intent(v.getContext(), RegisterActivity.class);
             LoginActivity.this.startActivity(registerIntent);
            }
        });
    }

}
