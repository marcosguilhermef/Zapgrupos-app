package com.origin.zapgrupos.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.origin.zapgrupos.models.Categorias.CategoriaModel;

public class HomeViewModel extends ViewModel {

    public static MutableLiveData<CategoriaModel> categoria;
    private static MutableLiveData<Boolean> downloaded;

    public HomeViewModel() {
        downloaded = new MutableLiveData<>();
        downloaded.setValue(false);
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