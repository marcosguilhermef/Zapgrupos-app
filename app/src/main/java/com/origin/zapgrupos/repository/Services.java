package com.origin.zapgrupos.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.load.model.Model;
import com.origin.zapgrupos.models.AdiconarGrupo.ErrosResponse;
import com.origin.zapgrupos.models.AdiconarGrupo.SucessoResponse;
import com.origin.zapgrupos.models.Categorias.CategoriaModel;
import com.origin.zapgrupos.models.Error.ErrorMensage;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.ListaDeGrupos;
import com.origin.zapgrupos.ui.AdicionarGrupo.AdicionarGrupoViewModel;
import com.origin.zapgrupos.ui.home.ListaDeCategorias;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Services {
    ZapgruposService service;
    static Retrofit retrofit;
    public Services(){
        retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://zapgrupos.xyz/")
                .build();
        service = retrofit.create(ZapgruposService.class);
    }

    public void getCategories(
            MutableLiveData<CategoriaModel> livedata,
            MutableLiveData<ErrorMensage> livedataErro){

        Call<CategoriaModel> callAsync = service.getCategories();
        callAsync.enqueue(
                new ResponseRetrofit<CategoriaModel>()
                        .getCallback(livedata, livedataErro)
        );
    }
    public void getGroupsForCategory(
            String category,MutableLiveData<ListaDeGrupos> livedata,
            MutableLiveData<ErrorMensage> livedataErro) {

        Call<ListaDeGrupos> callAsync = service.getFromCategory(category, 500, 1);
        callAsync.enqueue(
                new ResponseRetrofit<ListaDeGrupos>()
                        .getCallback(livedata, livedataErro)
        );
    }
    public void addNewGroup(
            Grupo document,
            MutableLiveData<SucessoResponse> successLiveData,
            MutableLiveData<ErrosResponse> errorLiveDataErrorResponse,
            MutableLiveData<ErrorMensage> livedataErro
            ) {

        Call<SucessoResponse> callAsync = service.addGroup(document);

        callAsync.enqueue(
                new ResponseRetrofit<SucessoResponse>()
                        .getCallback(
                                successLiveData,
                                errorLiveDataErrorResponse,
                                livedataErro
                        )
        );
    }
}