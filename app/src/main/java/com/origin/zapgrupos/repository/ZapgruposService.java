package com.origin.zapgrupos.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.common.util.concurrent.ListenableFuture;
import com.origin.zapgrupos.models.AdiconarGrupo.ErrosResponse;
import com.origin.zapgrupos.models.AdiconarGrupo.SucessoResponse;
import com.origin.zapgrupos.models.Categorias.CategoriaModel;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.ListaDeGrupos;

import org.json.JSONObject;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ZapgruposService {
    @GET("api/grupos/{categoria}")
    Call<ListaDeGrupos> getFromCategory(
            @Path("categoria") String categoria,
            @Query("limit") Integer limit,
            @Query("page") Integer page
            );
    @GET("api/grupos/{categoria}")
    Single<ListaDeGrupos> getFromCategoryLF(
            @Path("categoria") String categoria,
            @Query("limit") Integer limit,
            @Query("page") Integer page
    );
    @GET("api/mais")
    Call<CategoriaModel> getCategories();
    @POST("api/add-grupo")
    Call<SucessoResponse> addGroup(@Body Grupo body);
    @POST("api/grupo/sesivel/{id}")
    Call<Object> markSensivel(@Path("id") String id);

}
