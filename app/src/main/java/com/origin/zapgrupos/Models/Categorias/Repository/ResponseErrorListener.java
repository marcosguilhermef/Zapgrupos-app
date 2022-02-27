package com.origin.zapgrupos.Models.Categorias.Repository;

import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

public class ResponseErrorListener implements ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("http erro", error.getMessage());
    }
}
