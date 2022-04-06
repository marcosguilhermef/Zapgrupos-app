package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.origin.zapgrupos.R;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.ListaDeGrupos;
import com.origin.zapgrupos.databinding.FragmentListaGruposPorCategoriaBinding;
import com.origin.zapgrupos.repository.Services;
import com.origin.zapgrupos.ui.custonlistners.onChangeTitle;


public class ListaGruposPorCategoriaFragment extends Fragment implements onChangeTitle {

    private FragmentListaGruposPorCategoriaBinding binding;
    private onChangeTitle mListener;
    private GruposViewModel viewModel;
    private Bundle bundle;
    private InterstitialAd mInterstitialAd;
    private Parcelable state;

    /*
     * Algumas mudanças devem ser implementadas aqui. Algumas delas para adequar o código a seguir o padrão recomendado pelo google.
     * 1) Subir viewModel e seus observables para o ciclo de vida onCreate();
     * 2) Armazenar dados de carregamento no banco de dados;
     *
     * */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            bundle = new Bundle();
        }
        Adapter adapter = new Adapter(new Comparator(), getContext(), getArguments().getString("categoria"));

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new GruposViewModel(getArguments().getString("categoria"));
            }
        };


        viewModel = new ViewModelProvider(this, factory).get(GruposViewModel.class);

        //viewModel = new ViewModelProvider(this).get(ListaDeGruposPorCategoriaViewModel.class);

        mListener.onFragmentInteraction(getArguments().getString("categoria"));


        binding = FragmentListaGruposPorCategoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.pagingDataFlow.subscribe(grupoPagingSource -> {
            adapter.submitData(getLifecycle(), grupoPagingSource);
        });

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        adapter.withLoadStateHeader(new GruposLoadState(v -> {
            adapter.retry();
        }));

        adapter.withLoadStateFooter(
                new GruposLoadState(v -> {
                    adapter.retry();
                })
        );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //binding.recyclerViewMovies.addItemDecoration(new GridSpace(2, 20, true));
        binding.recyclerViewMovies.setAdapter(
                adapter
        );

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
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(getContext(), getString(R.string.banner_ad_unit_id_intersticial), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.d("Ads TAG", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Log.d("Ads TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                mInterstitialAd = null;
                            }
                        });
                        mInterstitialAd.show(getActivity());
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("Ads", "mInterstitialAd " + loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(String title) {

    }

}