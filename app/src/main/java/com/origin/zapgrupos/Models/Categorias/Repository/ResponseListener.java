package com.origin.zapgrupos.Models.Categorias.Repository;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.origin.zapgrupos.Models.Categorias.CategoriaModel;

import org.json.JSONObject;


public class ResponseListener implements Listener<JSONObject> {
    private CategoriaModel response;
    private Requests r;
    final MutableLiveData<CategoriaModel> mutableLiveData = new MutableLiveData<>();
    Class Model;
    private CategoriaModel instance;

    ResponseListener(Requests r){
        Log.i("ResponseListener", "resposta de sucesso disparada");
        this.r = r;
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.i("resposta",response.toString());
        Gson g = new Gson();
        CategoriaModel modelJson = g.fromJson(response.toString(),CategoriaModel.class);
        setResponse(modelJson);
        setResponseLiveData(modelJson);
    }
    private void setResponse(CategoriaModel response){
        this.response = response;
    }

    public void setResponseLiveData(CategoriaModel response){
        try{
            mutableLiveData.setValue(response);
        }catch (Exception e){
            mutableLiveData.setValue(null);
            Log.i("carregando", e.getMessage());
        }
    }

    public MutableLiveData<CategoriaModel> getResponselivedata(){
        return mutableLiveData;
    }

    public CategoriaModel getResponse(){
        return response;
    }

    private void serializeJSON(){ }

    public void getBodySerialized(){ }

}
