package com.origin.zapgrupos.repository;

import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;

import org.junit.Test;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class TestPageSoucer {

    @Test
    public void proccess(){
        Flowable<PagingData<Grupo>> pagingDataFlow;
        PagingSource paginsgSourcieGrupo = new PagingSource("Amizade");

        // Create new Pager
        Pager<Integer, Grupo> pager = new Pager(
                // Create new paging config
                new PagingConfig(20, // pageSize - Count of items in one page
                        20, // prefetchDistance - Number of items to prefetch
                        false, // enablePlaceholders - Enable placeholders for data which is not yet loaded
                        20, // initialLoadSize - Count of items to be loaded initially
                        20 * 499),// maxSize - Count of total items to be shown in recyclerview
                () -> paginsgSourcieGrupo); // set paging source

        // inti Flowable
        pagingDataFlow = PagingRx.getFlowable(pager);
        //CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        //PagingRx.cachedIn(pagingDataFlow, coroutineScope);
    }

}
