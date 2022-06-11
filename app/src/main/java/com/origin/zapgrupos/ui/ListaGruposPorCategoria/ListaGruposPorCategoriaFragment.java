package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.origin.zapgrupos.databinding.FragmentListaGruposPorCategoriaBinding;
import com.origin.zapgrupos.ui.custonlistners.onChangeTitle;
import com.origin.zapgrupos.until.ads.Analytics;


public class ListaGruposPorCategoriaFragment extends Fragment implements onChangeTitle {

    private FragmentListaGruposPorCategoriaBinding binding;
    private onChangeTitle mListener;
    private GruposViewModel viewModel;
    private Bundle bundle;
    private InterstitialAd mInterstitialAd;
    private Parcelable state;
    Adapter adapter;

    /*
     * Algumas mudanças devem ser implementadas aqui. Algumas delas para adequar o código a seguir o padrão recomendado pelo google.
     * 1) Subir viewModel e seus observables para o ciclo de vida onCreate();
     * 2) Armazenar dados de carregamento no banco de dados;
     *
     * */
    @Override
    public void onStart() {
        super.onStart();
        Analytics.ScreenNameSend(getArguments().getString("categoria"), this.getClass().getName());
        Log.i("ADSADS","chamando ads");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            bundle = new Bundle();
        }
        adapter = new Adapter(new Comparator(), getContext(), getActivity());

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new GruposViewModel(getArguments().getString("categoria"));
            }
        };

        viewModel = new ViewModelProvider(this, factory).get(GruposViewModel.class);

        viewModel.pagingDataFlow.subscribe(grupoPagingSource -> {
            adapter.submitData(getLifecycle(), grupoPagingSource);
        });

    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListaGruposPorCategoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        binding.recyclerViewMovies.setAdapter(adapter.withLoadStateFooter(new GruposLoadState(v -> {
                    adapter.retry();
                })
        ));

        adapter.addLoadStateListener(loadStates -> {
            LoadState refreshLoadState = loadStates.getRefresh();
            if(refreshLoadState instanceof LoadState.Loading){
                progressBarVisibility(View.VISIBLE);
            }else{
                progressBarVisibility(View.GONE);
                recivleView(View.VISIBLE);
            }
            return null;
        });

        binding.recyclerViewMovies.setLayoutManager(linearLayoutManager);
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.pagingDataFlow = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (onChangeTitle) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        mListener.onFragmentInteraction(getArguments().getString("categoria"));
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(String title) {

    }

    private void progressBarVisibility(int visibility){
        binding.loadAds.progressBarGrupos.setVisibility(visibility);
    }
    private void recivleView(int visibility){
        binding.recyclerViewMovies.setVisibility(visibility);
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}