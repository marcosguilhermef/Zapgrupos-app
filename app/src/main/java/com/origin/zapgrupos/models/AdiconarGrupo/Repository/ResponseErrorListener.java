package com.origin.zapgrupos.models.AdiconarGrupo.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.origin.zapgrupos.models.AdiconarGrupo.ErrosResponse;


import java.io.UnsupportedEncodingException;

public class ResponseErrorListener implements ErrorListener {

    private static MutableLiveData<ErrosResponse> mutableLiveData = new MutableLiveData<>();
    private ErrosResponse errosResponse;
    @Override
    public void onErrorResponse(VolleyError error) {
        if(error.networkResponse.data !=null ) {
            try {
                Log.i("carregando json", "carregando json livedata");
                Gson g = new Gson();
                String response = new String(error.networkResponse.data,"UTF-8");
                ErrosResponse modelJson = g.fromJson(response,ErrosResponse.class);
                setResponse(modelJson);
                setResponseLiveData(modelJson);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void setResponse(ErrosResponse erro){
        this.errosResponse = erro;
    }
    public ErrosResponse getError(){
        return this.errosResponse;
    }
    private void setResponseLiveData(ErrosResponse erro){
        mutableLiveData.setValue(erro);
    }
    public MutableLiveData<ErrosResponse> getResponselivedata(){
        return mutableLiveData;
    }
    public static MutableLiveData<ErrosResponse> factoryLiveData(){
        return mutableLiveData;
    }

}
