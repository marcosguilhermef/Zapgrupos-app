package com.origin.zapgrupos.models.Categorias.Repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.origin.zapgrupos.models.Categorias.CategoriaModel;

import java.net.URL;


public class Requests{
    private static Context ctx;
    private static Requests instance;
    private static RequestQueue requestQueue;
    public final java.net.URL URL;
    private final JsonObjectRequest stringRequest;
    CategoriaModel successResponse;
    private MutableLiveData multableLiveData;
    private Class Model;
    private ResponseListener ResponseListener;
    private ResponseErrorListener ResponseErrorListener;

    public Requests(URL url, Context context, Class M) {
        URL = url;
        ctx = context;
        Model = M;
        requestQueue = getRequestQueue();
        ResponseListener = new ResponseListener(this);
        ResponseErrorListener = new ResponseErrorListener();
        stringRequest = new JsonObjectRequest(
                Request.Method.GET,url.toString(),
                null,
                ResponseListener,
                ResponseErrorListener);
        stringRequest
                .setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private synchronized static RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public Requests rum(){
        Request r = getRequestQueue().add(stringRequest);
        return this;
    }
    public CategoriaModel getResponse(){
        return ResponseListener.getResponse();
    }
    public MutableLiveData getResponseLiveData(){
        return ResponseListener.getResponselivedata();
    }

}
