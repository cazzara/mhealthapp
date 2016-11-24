package com.example.cazza223.mhealthapp;



import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by cazza223 on 10/24/2016.
 * Modified from https://mc-programming.blogspot.com/2014/10/android-volley-https-with-self-signed.html
 */

public class VolleyProvider{
    private static VolleyProvider instance;
    private RequestQueue queue;
    private HurlStack hurlStack;

    private VolleyProvider(Context ctx){
        try {

            TLSSocketFactory tlsSocketFactory = new TLSSocketFactory(ctx);


            hurlStack = new HurlStack(null, tlsSocketFactory);
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);
            queue = Volley.newRequestQueue(ctx, hurlStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static VolleyProvider getInstance(Context ctx){
        if(instance == null){
            instance = new VolleyProvider(ctx);
        }
        return instance;
    }

    public RequestQueue getQueue(){
        return this.queue;
    }

    public <T> Request<T> addRequest(Request<T> req) {
        return getQueue().add(req);
    }

    public <T> Request<T> addRequest(Request<T> req, String tag) {
        req.setTag(tag);
        return getQueue().add(req);
    }

}