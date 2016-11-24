package com.example.cazza223.mhealthapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText et_fname = (EditText) findViewById(R.id.et_fname);
        final EditText et_lname = (EditText) findViewById(R.id.et_lname);
        final EditText et_username = (EditText) findViewById(R.id.et_username);
        final EditText et_passwd = (EditText) findViewById(R.id.et_passw);

        final Button bRegister = (Button) findViewById(R.id.bRegister);



        bRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                final String fname = et_fname.getText().toString();
                final String lname = et_lname.getText().toString();
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
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };



                RegisterRequest registerRequest = new RegisterRequest(username, password, fname, lname, responseListener,  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

//                try {
//                    SSLContext sc = SSLContext.getInstance("SSLv3");
//                    sc.init(null, null, new java.security.SecureRandom());
//
//                    SSLSocketFactory sf = sc.getSocketFactory();
//                    HurlStack hs = new HurlStack(null, sf);
//                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this, hs);
//                    queue.add(registerRequest);
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                } catch (KeyManagementException e) {
//                    e.printStackTrace();
//                }

//                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
//                queue.add(registerRequest);
                VolleyProvider.getInstance(RegisterActivity.this).addRequest(registerRequest);


            }
        });
    }
}
