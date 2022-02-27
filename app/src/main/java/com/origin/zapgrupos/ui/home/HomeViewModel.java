package com.origin.zapgrupos.ui.home;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.origin.zapgrupos.Models.Categorias.Repository.Requests;
import com.origin.zapgrupos.Models.Categorias.CategoriaModel;

import java.net.MalformedURLException;
import java.net.URL;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private static MutableLiveData<CategoriaModel> categoria;
    private static MutableLiveData<Boolean> downloaded;
    final private String url = "https://zapgrupos.xyz/api/mais";

    public HomeViewModel() {
        downloaded = new MutableLiveData<>();
        downloaded.setValue(false);
    }

    private void initRequest(){ }

    public LiveData<String> getText() {
        return mText;
    }

    public void setDownloaded(Boolean value) {
        downloaded.setValue(value);
    }

    public LiveData<Boolean> getDownloaded(){
        return downloaded;
    }

    public void setCategoria(MutableLiveData<CategoriaModel> categoria) {
        this.categoria = categoria;
    }

    public MutableLiveData<CategoriaModel> getCategoria(){
        if(categoria == null){
            return null;
        }
        return categoria;
    }
}