package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
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
    private ListaDeGruposPorCategoriaViewModel viewModel;
    private Bundle bundle;
    private InterstitialAd mInterstitialAd;
    private Parcelable state;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState == null){
            bundle = new Bundle();
        }

        viewModel = new ViewModelProvider(this).get(ListaDeGruposPorCategoriaViewModel.class);

        mListener.onFragmentInteraction(getArguments().getString("categoria"));
        binding = FragmentListaGruposPorCategoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getDownloaded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    makeRequest();
                    viewModel.setDownloaded(true);
                }
            }
        });
        viewModel.getGrupos().observe(getViewLifecycleOwner(), new Observer<ListaDeGrupos>() {
            @Override
            public void onChanged(@Nullable ListaDeGrupos grupos) {
                if(grupos != null){
                    MontarListaDeGrupos(grupos);
                    binding.progressBarGrupos.setVisibility(binding.progressBarGrupos.GONE);
                }
            }
        });
        binding.listViewGrupos.setOnItemClickListener(onClickItemCategory());
        return root;
    }

    private void makeRequest(){
        Services request = new Services();
        request.getGroupsForCategory(
                getArguments().getString("categoria"),
                viewModel.getGrupos(),
                viewModel.getErro()
        );
    }

    private void MontarListaDeGrupos(ListaDeGrupos grupos){
        ListView listView = binding.listViewGrupos;
        listView.setAdapter(new ListaDeGruposAdapter(getActivity(), grupos.getData()));
    }

    private AdapterView.OnItemClickListener onClickItemCategory(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = binding.listViewGrupos.getItemAtPosition(position);
                Grupo group = (Grupo) o;
                bundle.putString("_id",group.get_id());
                bundle.putString("titulo",group.getTitulo());
                bundle.putString("url",group.getUrl());
                bundle.putString("img",group.getImg() != null ? group.getImg().get(0) : null);
                bundle.putString("categoria",group.getCategoria());
                bundle.putString("descricao",group.getDescricao());
                bundle.putBoolean("sensivel",group.getSensivel());
                Navigation.findNavController(v).navigate(R.id.nav_grupo, bundle);
            }
        };
    }
    @Override
    public void onPause() {
        super.onPause();
        state = binding.listViewGrupos.onSaveInstanceState();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(state != null){
            binding.listViewGrupos.onRestoreInstanceState(state);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        binding = null;
        viewModel.setCategoria(null);
        viewModel.setDownloaded(false);
        state = null;
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
    public void onViewCreated(View view, Bundle bundle){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(getContext(),getString(R.string.banner_ad_unit_id_intersticial), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
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
                        Log.i("Ads", "mInterstitialAd "+loadAdError.getMessage());
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