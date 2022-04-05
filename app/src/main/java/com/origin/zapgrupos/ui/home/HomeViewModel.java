package com.origin.zapgrupos.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.origin.zapgrupos.models.Categorias.CategoriaModel;
import com.origin.zapgrupos.models.Error.ErrorMensage;

public class HomeViewModel extends ViewModel {

    public static MutableLiveData<CategoriaModel> categoria;
    private static MutableLiveData<Boolean> downloaded;
    public MutableLiveData<ErrorMensage> erro;

    public HomeViewModel() {
        categoria = new MutableLiveData<>();
        downloaded = new MutableLiveData<>();
        downloaded.setValue(false);
        erro = new MutableLiveData<>(null);
    }
    public void setDownloaded(Boolean value) {
        downloaded.setValue(value);
    }
    public static MutableLiveData<Boolean> getDownloaded() { return downloaded; }
    public void setCategoria(MutableLiveData<CategoriaModel> categoria) { this.categoria = categoria; }
    public MutableLiveData<CategoriaModel> getCategoria(){
        return categoria;
    }
    public static void setDownloaded(MutableLiveData<Boolean> downloaded) {
        HomeViewModel.downloaded = downloaded;
    }
    public MutableLiveData<ErrorMensage> getErro() {
        return erro;
    }
    public void setErro(MutableLiveData<ErrorMensage> erro) {
        this.erro = erro;
    }
}