package com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.ListaDeGrupos;

import java.net.URL;


public class Requests{
    private static Context ctx;
    private static RequestQueue requestQueue;
    public final java.net.URL URL;
    private final JsonObjectRequest stringRequest;
    private ResponseListener ResponseListener;
    private ResponseErrorListener ResponseErrorListener;

    public Requests(URL url, Context context) {
        Log.i("URL: ",url.toString());
        URL = url;
        ctx = context;
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
    public ListaDeGrupos getResponse(){
        return ResponseListener.getResponse();
    }
    public MutableLiveData getResponseLiveData(){
        return ResponseListener.getResponselivedata();
    }

}
