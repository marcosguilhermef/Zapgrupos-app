package com.origin.zapgrupos.ui.AdicionarGrupo;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.origin.zapgrupos.models.AdiconarGrupo.ErrosResponse;
import com.origin.zapgrupos.models.AdiconarGrupo.SucessoResponse;
import com.origin.zapgrupos.models.Error.ErrorMensage;

public class AdicionarGrupoViewModel extends ViewModel {

    public MutableLiveData<SucessoResponse> sucesso;
    public MutableLiveData<ErrosResponse> erro;
    public MutableLiveData<ErrorMensage> errorNetwork;

    public AdicionarGrupoViewModel() {
        sucesso = new MutableLiveData<>(null);
        erro = new MutableLiveData<>(null);
        errorNetwork = new MutableLiveData<>(null);
    }

    public void setSucesso(SucessoResponse sucesso) { this.sucesso.setValue(sucesso); }

    public void setSucessoLivedata(MutableLiveData<SucessoResponse> sucesso) { this.sucesso = sucesso; }

    public MutableLiveData<SucessoResponse> getSucesso(){
        return sucesso;
    }

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