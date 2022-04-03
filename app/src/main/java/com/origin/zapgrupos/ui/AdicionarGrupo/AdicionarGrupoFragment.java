package com.origin.zapgrupos.ui.AdicionarGrupo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.origin.zapgrupos.R;
import com.origin.zapgrupos.databinding.FragmentAdicionarGrupoBinding;
import com.origin.zapgrupos.models.AdiconarGrupo.ErrosResponse;
import com.origin.zapgrupos.models.AdiconarGrupo.SucessoResponse;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.origin.zapgrupos.models.AdiconarGrupo.Repository.Requests;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;
import com.origin.zapgrupos.ui.home.HomeViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
public class AdicionarGrupoFragment extends Fragment {

    final private String url = "https://zapgrupos.xyz/api/add-grupo";
    private AdicionarGrupoViewModel adicionarGrupoViewModel;
    private FragmentAdicionarGrupoBinding binding;
    private InterstitialAd mInterstitialAd;
    private Bundle bundle;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        if(bundle == null){
            bundle = new Bundle();
        }
        adicionarGrupoViewModel = new ViewModelProvider(this).get(AdicionarGrupoViewModel.class);

        binding = FragmentAdicionarGrupoBinding.inflate(inflater, container, false);

        View root = binding.getRoot();


        adicionarGrupoViewModel.getSucesso().observe(getViewLifecycleOwner(), new Observer<SucessoResponse>(){
            @Override
            public void onChanged(SucessoResponse sucessoResponse) {
                mensagemDeSucesso(sucessoResponse);
            }
        } );

        adicionarGrupoViewModel.getErro().observe(getViewLifecycleOwner(), new Observer<ErrosResponse>(){
            @Override
            public void onChanged(ErrosResponse errosResponse) {
                showErro(errosResponse);
            }
        } );

        binding.enviraGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendGroup();
            }
        });

        String[] a = new String[]{
                "Portugal",
                "Brasil",
                "Moçambique",
                "Angola",
                "Cabo Verde",
                "Guiné-Bissau",
                "Guiné Equatorial",
                "São Tomé e Príncipe",
                "Timor-Leste"
        };
        String[] b = new String[]{
                "whatsapp",
                "telegram"
        };

        List<String> paises    = Arrays.asList(a);
        List<String> categoria = HomeViewModel.categoria.getValue().getData().stream().map( e -> e.getCategoria()).collect(Collectors.toList());
        ArrayAdapter<String> pais = new ArrayAdapter<>(getContext(),R.layout.list_item_pais, R.id.list_item, paises);
        ArrayAdapter<String> categorias = new ArrayAdapter<>(getContext(),R.layout.list_item_pais, R.id.list_item, categoria);
        ArrayAdapter<String> tipo = new ArrayAdapter<>(getContext(),R.layout.list_item_pais, R.id.list_item, Arrays.asList(b));
        binding.MenuPaisContent.setAdapter(pais);
        binding.MenuCategoriaInputView.setAdapter(categorias);
        binding.MenuTipoInputView.setAdapter(tipo);
        return root;
    }

    private void sendGroup(){
        try{
            Requests Re = new Requests(new URL(url), getContext(), getData()).rum();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private JSONObject getData() throws JSONException {
        JSONObject json = new JSONObject();
            json.put("link", binding.TextLinkInput.getText());
            json.put("descricao", binding.TextDescricaoImput.getText());
            json.put("email", binding.TextEmailImput.getText());
            json.put("telefone", binding.TextTelefoneImput.getText());
            json.put("categoria", binding.MenuCategoriaInputView.getText());
            json.put("tipo", binding.MenuTipoInputView.getText());
            json.put("pais", binding.MenuPaisContent.getText());
        return json;
    }

    private void showErro(ErrosResponse errosResponse)  {
        if(errosResponse.getLink() != null){
            binding.TextLink.setError(
                    errosResponse
                            .getLink()
                            .stream()
                            .collect(
                                    Collectors.joining("\n")
                            )
            );
        }else{
            binding.TextLink.setErrorEnabled(false);
        }
        if(errosResponse.getDescricao() != null){
            binding.TextDescricao.setError(
                    errosResponse
                            .getDescricao()
                            .stream()
                            .collect(
                                    Collectors.joining("\n")
                            )
            );
        }else{
            binding.TextDescricao.setErrorEnabled(false);
        }

        if(errosResponse.getCategoria() != null){
            binding.MenuCategoria.setError(
                    errosResponse
                            .getCategoria()
                            .stream()
                            .collect(
                                    Collectors.joining("\n")
                            )
            );
        }else{
            binding.MenuCategoria.setErrorEnabled(false);
        }

        if(errosResponse.getCategoria() !=null){
            binding.MenuCategoria.setError(
                    errosResponse
                            .getCategoria()
                            .stream()
                            .collect(
                                    Collectors.joining("\n")
                            )
            );
        }else{
            binding.MenuCategoria.setErrorEnabled(false);
        }

        if(errosResponse.getTelefone() != null){
            binding.TextTelefone.setError(
                    errosResponse
                            .getTelefone()
                            .stream()
                            .collect(
                                    Collectors.joining("\n")
                            )
            );
        }
        else{
            binding.TextTelefone.setErrorEnabled(false);
        }

        if(errosResponse.getEmail() != null){
            binding.TextEmail.setError(
                    errosResponse
                            .getEmail()
                            .stream()
                            .collect(
                                    Collectors.joining("\n")
                            )
            );
        }else{
            binding.TextEmail.setErrorEnabled(false);
        }
    }
    private void mensagemDeErro(String erro){
        new MaterialAlertDialogBuilder(getContext(),R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setMessage("Algum erro foi detectado: "+erro)
                .show();
    }
    private void mensagemDeSucesso(SucessoResponse sucessoResponse){
        new MaterialAlertDialogBuilder(getContext(),R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setMessage(R.string.mensagem_sucesso)
                .setPositiveButton(R.string.sim, (v,i) ->{
                    bundle.putString("_id",sucessoResponse.getId());
                    bundle.putString("url",sucessoResponse.getUrl());
                    bundle.putString("img",null);
                    bundle.putString("categoria",sucessoResponse.getCategoria());
                    bundle.putString("descricao",sucessoResponse.getDescricao());
                    bundle.putBoolean("sensivel",true);
                    bundle.putString("tipo",sucessoResponse.getTipo());
                    Navigation.findNavController(this.getView()).navigate(R.id.nav_grupo, bundle);
                })
                .setNegativeButton(R.string.nao, (v,i) -> {
                    v.cancel();
                })
                .show();
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
                                Log.d("Ads TAG", "The ad was shown.");
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onDetach(){
        super.onDetach();

    }

}