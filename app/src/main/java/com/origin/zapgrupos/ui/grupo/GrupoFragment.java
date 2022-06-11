package com.origin.zapgrupos.ui.grupo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.origin.zapgrupos.R;
import com.origin.zapgrupos.databinding.FragmentGrupoBinding;
import com.origin.zapgrupos.ui.custonlistners.onChangeTitle;
import com.origin.zapgrupos.util.analytics.Analytics;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GrupoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GrupoFragment extends Fragment {
    private FragmentGrupoBinding binding;
    private AdView adView;
    private onChangeTitle mListener;
    private InterstitialAd mInterstitialAd;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "titulo";
    private static final String ARG_PARAM2 = "url";
    private static final String ARG_PARAM3 = "img";
    private static final String ARG_PARAM4 = "descricao";
    private static final String ARG_PARAM5 = "categoria";
    private static final String ARG_PARAM6 = "_id";
    private static final String ARG_PARAM7 = "sensivel";
    private static final String ARG_PARAM8 = "tipo";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;
    private Boolean mParam7;
    private String mParam8;
    private static int counterClick = 0;
    private final int PARSER = 1;
    public GrupoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GrupoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GrupoFragment newInstance(String param1, String param2) {
        GrupoFragment fragment = new GrupoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
            mParam7 = getArguments().getBoolean(ARG_PARAM7);
            mParam8 = getArguments().getString(ARG_PARAM8);
        }
        Analytics.ScreenNameSend(mParam1+" ("+mParam6+")",this.getClass().getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGrupoBinding.inflate(inflater, container, false);

        binding.grupoTitulo.setText(mParam1 == null ? "" : mParam1);
        binding.grupoDescricao.setText(mParam4 == null ? "" : mParam4);
        initADS();
        if(mParam7 == false || mParam7 == null || mParam3 == null){
            Glide.with(getContext())
                    .load(mParam3)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_error_outline_24)
                    .into(binding.grupoImagem);
        }{
            binding.grupoImagem.setImageResource(R.drawable.ic_baseline_error_outline_24);
        }

        binding.EntrarButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("url:", mParam2);
                Analytics.join(mParam6,mParam1);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse( mParam2 ));
                startActivity(viewIntent);

            }
        }  );

        binding.compartilhar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                Analytics.share(mParam6,mParam1);
                mParam5 =mParam5.replace(" ","-");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "" +
                        "Olá, eu obti esse grupo através do zapgrupos. " +
                        "Aplicativo que está disponível na playstore! " +
                        "Entre no link abaixo para acessar esse grupo de whatsapp que eu achei legal: " +
                        "https://zapgrupos.xyz/"+mParam5+"/"+mParam6 );
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);


            }
        });

        View view = binding.getRoot();
        return view;
    }

    private void hideProgressBar(){
        binding.progressBarGrupos.setVisibility(View.GONE);
    }

    private void showProgressBar(){
        binding.progressBarGrupos.setVisibility(View.VISIBLE);

    }



    private void initADS(){
        AdRequest adRequest = new AdRequest.Builder().build();

        if(mInterstitialAd != null || (counterClick % PARSER == 0) ){
            fullscreeam();
            InterstitialAd.load(getContext(), getString(R.string.banner_ad_unit_id_intersticial), adRequest, interticialCallBack());
            count();
        }else{
           hideProgressBar();
           count();
        }
    }
    private void count(){
        counterClick++;
    }

    private InterstitialAdLoadCallback interticialCallBack(){
        return new InterstitialAdLoadCallback(){
            public void onAdFailedToLoad( LoadAdError loadAdError) {
                hideProgressBar();
                showscream();
                mInterstitialAd = null;
            }

            public void onAdLoaded(InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                mInterstitialAd.show(getActivity());
                mInterstitialAd.setFullScreenContentCallback(fullScreeeCallBack());
                mInterstitialAd = null;
            }
        };
    }

    private FullScreenContentCallback fullScreeeCallBack(){
        return new FullScreenContentCallback() {

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressBar();
                        showscream();
                    }
                }, 500);


            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressBar();
                        showscream();
                    }
                }, 500);
            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
            }
        };
    }

    public void onViewCreated(View view, Bundle bundle){
        super.onViewCreated(view,bundle);
        mListener.onFragmentInteraction(mParam1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    private void fullscreeam(){
        ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();

    }

    private void showscream(){
        ((AppCompatActivity) requireActivity()).getSupportActionBar().show();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}