package com.origin.zapgrupos.models.AdiconarGrupo.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.origin.zapgrupos.models.AdiconarGrupo.ErrosResponse;
import com.origin.zapgrupos.models.AdiconarGrupo.SucessoResponse;

import org.json.JSONObject;

import java.net.URL;

import javax.xml.transform.ErrorListener;


public class Requests{
    private static Context ctx;
    private static RequestQueue requestQueue;
    public final java.net.URL URL;
    private final JsonObjectRequest stringRequest;
    private ResponseListener ResponseListener;
    private ResponseErrorListener ResponseErrorListener;

    public Requests(URL url, Context context, JSONObject body) {
        URL = url;
        ctx = context;
        requestQueue = getRequestQueue();
        ResponseListener = new ResponseListener(this);
        ResponseErrorListener = new ResponseErrorListener();
        Log.i("corpo: ", body.toString());
        stringRequest = new JsonObjectRequest(
                Request.Method.POST,url.toString(),
                body,
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
    public SucessoResponse getResponse(){
        return ResponseListener.getResponse();
    }
    public ErrosResponse getErros(){
        return ResponseErrorListener.getError();
    }
    public MutableLiveData<SucessoResponse> getResponseLiveData(){
        return ResponseListener.getResponselivedata();
    }
    public MutableLiveData<ErrosResponse> getErrorLiveData(){
        return ResponseErrorListener.getResponselivedata();
    }

}
