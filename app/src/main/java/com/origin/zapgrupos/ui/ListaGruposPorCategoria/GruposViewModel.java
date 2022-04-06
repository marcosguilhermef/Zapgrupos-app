package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;
import com.origin.zapgrupos.repository.PagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;
/*
    ViewModel for MainActivity
 */
public class GruposViewModel extends ViewModel {
    // Define Flowable for movies
    public Flowable<PagingData<Grupo>> pagingDataFlow;

    public GruposViewModel(String categoria) {
        Log.i("Recriado","--------------------------------------[RECRIADO]------------------------------");
        init(categoria);
    }
 
    // Init ViewModel Data
    private void init(String categoria) {
        // Define Paging Source
        PagingSource grupoPagingSource = new PagingSource(categoria);
 
        // Create new Pager
        Pager<Integer, Grupo> pager = new Pager(
                // Create new paging config
                new PagingConfig(100, // pageSize - Count of items in one page
                        50, // prefetchDistance - Number of items to prefetch
                        true, // enablePlaceholders - Enable placeholders for data which is not yet loaded
                        20, // initialLoadSize - Count of items to be loaded initially
                        20000),// maxSize - Count of total items to be shown in recyclerview
                () -> grupoPagingSource); // set paging source
 
        // inti Flowable
        pagingDataFlow = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(pagingDataFlow, coroutineScope);
    }
}