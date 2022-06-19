package com.origin.zapgrupos.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import com.origin.zapgrupos.models.AdiconarGrupo.ErrosResponse;
import com.origin.zapgrupos.models.AdiconarGrupo.SucessoResponse;
import com.origin.zapgrupos.models.Categorias.CategoriaModel;
import com.origin.zapgrupos.models.Error.ErrorMensage;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Denuncia;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.ListaDeGrupos;


public class Services {
    ZapgruposService service;
    static Retrofit retrofit;
    public Services(){
        retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl("https://zapgrupos.xyz/")
                .build();
        service = retrofit.create(ZapgruposService.class);
    }

    public ZapgruposService getService(){
        return service;
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

    public void denunciar(String id){
        Denuncia d = new Denuncia();
        d.setId(id);
        Call<Object> callAsync = service.denuncia(d);
        callAsync.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
