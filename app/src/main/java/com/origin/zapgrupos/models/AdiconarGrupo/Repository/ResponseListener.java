package com.origin.zapgrupos.models.AdiconarGrupo.Repository;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.origin.zapgrupos.models.AdiconarGrupo.SucessoResponse;

import org.json.JSONObject;


public class ResponseListener implements Listener<JSONObject> {
    private SucessoResponse response;
    private Requests r;
    private static MutableLiveData<SucessoResponse> mutableLiveData = new MutableLiveData<>();
    private SucessoResponse instance;

    ResponseListener(Requests r){
        this.r = r;
    }

    @Override
    public void onResponse(JSONObject response) {
        Gson g = new Gson();
        SucessoResponse modelJson = g.fromJson(response.toString(),SucessoResponse.class);
        setResponse(modelJson);
        setResponseLiveData(modelJson);
    }
    private void setResponse(SucessoResponse response){
        this.response = response;
    }

    public void setResponseLiveData(SucessoResponse response){
        mutableLiveData.setValue(response);
    }

    public MutableLiveData<SucessoResponse> getResponselivedata(){
        return mutableLiveData;
    }

    public SucessoResponse getResponse(){
        return response;
    }

    private void serializeJSON(){ }

    public void getBodySerialized(){ }
    public static MutableLiveData<SucessoResponse> factoryLiveData(){
        return mutableLiveData;
    }
}
