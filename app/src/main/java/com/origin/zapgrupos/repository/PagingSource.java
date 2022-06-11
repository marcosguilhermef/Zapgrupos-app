package com.origin.zapgrupos.repository;

import android.util.Log;

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
    Integer lastpage;
    Integer currentPage;
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
                    .map((li) -> {
                        setPage(li.getLast_page(), li.getCurrent_page());
                        return li.getData();
                    })
                    .map( grupo -> toLoadResult(grupo, page))
                    .onErrorReturn(LoadResult.Error::new);
        } catch (Exception e) {
            return Single.just(new LoadResult.Error(e));
        }
    }
    private void setPage(Integer lastPage, Integer currentPage){
        this.currentPage = currentPage;
        this.lastpage    = lastPage;
    }
    private LoadResult<Integer, Grupo> toLoadResult(List<Grupo> grupo, int page) {
        return new LoadResult.Page(
                grupo,
                getPrev(page),
                getNext(grupo, page)
        );
    }

    private Integer getPrev(int page){
        return page == 1 ? null : page - 1;
    }

    private Integer getNext(List<Grupo> grupos,int page){
        if(grupos.isEmpty()){
            return null;
        }
        return page +1;
    }
    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Grupo> state) {
        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, Grupo> anchorPage = state.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        List<Grupo> grupo = anchorPage.getData();

        if(grupo.isEmpty()){
            return null;
        }

        return null;
    }
}