package com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Repository;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.ListaDeGrupos;

import org.json.JSONObject;


public class ResponseListener implements Listener<JSONObject> {
    private ListaDeGrupos response;
    private Requests r;
    final MutableLiveData<ListaDeGrupos> mutableLiveData = new MutableLiveData<>();

    ResponseListener(Requests r){
        this.r = r;
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.i("resposta",response.toString());
        Gson g = new Gson();
        ListaDeGrupos modelJson = g.fromJson(response.toString(),ListaDeGrupos.class);
        setResponse(modelJson);
        setResponseLiveData(modelJson);
    }
    private void setResponse(ListaDeGrupos response){
        this.response = response;
    }

    public void setResponseLiveData(ListaDeGrupos response){
        try{
            mutableLiveData.setValue(response);
        }catch (Exception e){
            mutableLiveData.setValue(null);
            Log.i("carregando", e.getMessage());
        }
    }

    public MutableLiveData<ListaDeGrupos> getResponselivedata(){
        return mutableLiveData;
    }

    public ListaDeGrupos getResponse(){
        return response;
    }

    private void serializeJSON(){ }

    public void getBodySerialized(){ }

}
