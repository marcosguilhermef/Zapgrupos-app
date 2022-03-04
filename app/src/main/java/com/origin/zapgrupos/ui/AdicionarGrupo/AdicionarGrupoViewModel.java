package com.origin.zapgrupos.ui.AdicionarGrupo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.origin.zapgrupos.models.AdiconarGrupo.ErrosResponse;
import com.origin.zapgrupos.models.AdiconarGrupo.Repository.ResponseErrorListener;
import com.origin.zapgrupos.models.AdiconarGrupo.Repository.ResponseListener;
import com.origin.zapgrupos.models.AdiconarGrupo.SucessoResponse;
import com.origin.zapgrupos.models.Categorias.CategoriaModel;

public class AdicionarGrupoViewModel extends ViewModel {

    private static MutableLiveData<SucessoResponse> sucesso;
    private static MutableLiveData<ErrosResponse> erro;

    public AdicionarGrupoViewModel() {
        sucesso = ResponseListener.factoryLiveData();
        erro = ResponseErrorListener.factoryLiveData();
    }

    public void setSucesso(SucessoResponse sucesso) { this.sucesso.setValue(sucesso); }

    public void setSucessoLivedata(MutableLiveData<SucessoResponse> sucesso) { this.sucesso = sucesso; }

    public MutableLiveData<SucessoResponse> getSucesso(){
        return sucesso;
    }

    /*----------------------------------------------------------------*/

    public void setErro(ErrosResponse erro) {
        this.erro.setValue(erro);
    }

    public MutableLiveData<ErrosResponse> getErro(){ return erro; }

    public void setErroLiveData(MutableLiveData<ErrosResponse> erro) {
        this.erro = erro;
    }

    public MutableLiveData<ErrosResponse> getErroLiveData() {
        return erro;
    }

}