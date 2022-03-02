package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.ListaDeGrupos;

public class ListaDeGruposPorCategoriaViewModel extends ViewModel {

    private static MutableLiveData<ListaDeGrupos> grupos;
    private static MutableLiveData<Boolean> downloaded;

    public ListaDeGruposPorCategoriaViewModel() {
        downloaded = new MutableLiveData<>();
        downloaded.setValue(false);
    }
    public void setDownloaded(Boolean value) {
        downloaded.setValue(value);
    }

    public LiveData<Boolean> getDownloaded(){
        return downloaded;
    }

    public void setCategoria(MutableLiveData<ListaDeGrupos> grupos) {
        this.grupos = grupos;
    }

    public MutableLiveData<ListaDeGrupos> getGrupos(){
        if(grupos == null){
            return null;
        }
        return grupos;
    }

}
