package com.origin.zapgrupos.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.ListaDeGrupos;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PagingSource extends RxPagingSource<Integer, Grupo> {
    Services services;
    String categoria;
    public PagingSource(String query){
        services = new Services();
        categoria = query;

    }
    @NotNull
    @Override
    public Single<LoadResult<Integer, Grupo>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        try {
            int page = loadParams.getKey() != null ? loadParams.getKey() : 1;

            Single<ListaDeGrupos> response = services.getService().getFromCategoryLF(categoria,50,page);
            return response
                    .subscribeOn(Schedulers.io())
                    .map(ListaDeGrupos::getData)
                    .map( grupo -> toLoadResult(grupo, page))
                    .onErrorReturn(LoadResult.Error::new);
        } catch (Exception e) {
            return Single.just(new LoadResult.Error(e));
        }
    }

    private LoadResult<Integer, Grupo> toLoadResult(List<Grupo> grupo, int page) {
        return new LoadResult.Page(grupo, page == 1 ? null : page - 1, page + 1);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Grupo> pagingState) {

        return null;
    }
}