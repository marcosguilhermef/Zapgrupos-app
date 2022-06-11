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
    public Flowable<PagingData<Grupo>> pagingDataFlow;
    public Boolean loading;
    public GruposViewModel(String categoria) {
        init(categoria);
    }
 
    private void init(String categoria) {
        PagingSource grupoPagingSource = new PagingSource(categoria);
 
        Pager<Integer, Grupo> pager = new Pager(
                new PagingConfig(50, // pageSize - Count of items in one page
                        30, // prefetchDistance - Number of items to prefetch
                        false, // enablePlaceholders - Enable placeholders for data which is not yet loaded
                        50),// maxSize - Count of total items to be shown in recyclerview
                () -> grupoPagingSource); // set paging source
 
        // inti Flowable
        pagingDataFlow = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(pagingDataFlow, coroutineScope);
    }
}