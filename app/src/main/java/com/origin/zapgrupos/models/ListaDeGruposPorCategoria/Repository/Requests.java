package com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.ListaDeGrupos;

import org.json.JSONException;
import org.json.JSONObject;

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

    public Requests(URL url, Context context, Integer method) throws JSONException {
        Log.i("URL: ",url.toString());
        URL = url;
        ctx = context;
        requestQueue = getRequestQueue();
        ResponseListener = new ResponseListener(this);
        ResponseErrorListener = new ResponseErrorListener();
        JSONObject c = new JSONObject();
        c.put("token","Lm9yn5xqzEF1EOKUDvhh5avfZxnFiMTzB9i3fDhWjloU7b58IZ");
        stringRequest = new JsonObjectRequest(
                method, url.toString(),
                c,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("ok","enviado");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                        if(error.networkResponse != null){
                            Log.i("ok",String.valueOf(error.networkResponse.statusCode));
                        }
                    }
        });
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
