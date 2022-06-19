package com.origin.zapgrupos.ui.grupo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.origin.zapgrupos.models.Categorias.CategoriaModel;


public class GrupoViewModel extends ViewModel {
    public MutableLiveData<String> sucesso;

    public GrupoViewModel() {
        sucesso = new MutableLiveData<>();
    }

    public MutableLiveData<String> getSucesso(){
        return sucesso;
    }

}