package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.origin.zapgrupos.R;
import com.origin.zapgrupos.models.Categorias.CategoriaDataModel;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.ListaDeGrupos;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Repository.Requests;
import com.origin.zapgrupos.databinding.FragmentListaGruposPorCategoriaBinding;

import com.origin.zapgrupos.ui.custonlistners.onChangeTitle;

import java.net.MalformedURLException;
import java.net.URL;


public class ListaGruposPorCategoriaFragment extends Fragment implements onChangeTitle {

    private FragmentListaGruposPorCategoriaBinding binding;
    //final private String url = "https://zapgrupos.xyz/api/grupos/Amizade?page=1&limit=20";
    private onChangeTitle mListener;
    private ListaDeGruposPorCategoriaViewModel viewModel;
    private Requests Request;
    private Bundle bundle;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState == null){
            bundle = new Bundle();
        }

        viewModel = new ViewModelProvider(this).get(ListaDeGruposPorCategoriaViewModel.class);

        mListener.onFragmentInteraction(getArguments().getString("categoria"));
        binding = FragmentListaGruposPorCategoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        makeRequest();

        viewModel.getDownloaded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean b) {
                binding.progressBarGrupos.setVisibility( !b ? binding.progressBarGrupos.VISIBLE : binding.progressBarGrupos.GONE);
            }
        });

        viewModel.getGrupos().observe(getViewLifecycleOwner(), new Observer<ListaDeGrupos>() {
            @Override
            public void onChanged(@Nullable ListaDeGrupos grupos) {
                MontarListaDeGrupos(grupos);
            }
        });

        binding.listViewGrupos.setOnItemClickListener(onClickItemCategory());

        binding.listViewGrupos.setOnItemLongClickListener(onPressItemCategory());
        return root;
    }

    private void makeRequest(){
        viewModel.setCategoria(getGruposModelLiveData());
    }

    private MutableLiveData<ListaDeGrupos> getGruposModelLiveData(){
        if(viewModel.getGrupos() == null){
            try{
                final String url = "https://zapgrupos.xyz/api/grupos/"
                        +getArguments().getString("categoria")
                        +"?page=1"
                        +"&limit=500";
                URL urlFormated = new URL(url);
                Request = new Requests(urlFormated,getActivity().getApplicationContext());
                return Request.rum().getResponseLiveData();
            }catch (MalformedURLException e) {
                return null;
            }
        }
        return viewModel.getGrupos();
    }

    private void MontarListaDeGrupos(ListaDeGrupos grupos){
        ListView listView = binding.listViewGrupos;
        viewModel.setDownloaded(true);
        listView.setAdapter(new com.origin.zapgrupos.ui.ListaGruposPorCategoria.ListaDeGruposAdapter(getActivity(), grupos.getData()));
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
                bundle.putString("img",group.getImg().get(0));
                bundle.putString("categoria",group.getCategoria());
                bundle.putString("descricao",group.getDescricao());
                bundle.putBoolean("sensivel",group.getSensivel());
                Navigation.findNavController(v).navigate(R.id.nav_grupo, bundle);
            }
        };
    }

    private AdapterView.OnItemLongClickListener onPressItemCategory(){
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new MaterialAlertDialogBuilder(getContext(),R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                        .setMessage("Deseja marcar grupo como sensivel?")
                        .setNegativeButton(R.string.nao_marcar_como_sensivel, (d,w) -> {
                            d.cancel();
                        })
                        .setPositiveButton(R.string.sim_marcar_como_sensivel, (d,w) ->{
                            Object o = binding.listViewGrupos.getItemAtPosition(i);
                            Grupo group = (Grupo) o;
                            requisicaoImagemSensivel(group.get_id());
                        })
                        .show();
                return true;
            }
        };
    }

    private void requisicaoImagemSensivel(String ID){
        try{
            final String url = "https://zapgrupos.xyz/api/grupo/sesivel/"+ID;
            URL urlFormated = new URL(url);
            Request = new Requests(urlFormated,getActivity().getApplicationContext(), com.android.volley.Request.Method.PATCH).rum();
        }catch (Exception e) {
            new MaterialAlertDialogBuilder(getContext(),R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                    .setMessage("Aconteceu algum erro no processo de requsição")
                    .show();
            }
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        viewModel.setCategoria(null);
        viewModel.setDownloaded(false);

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(String title) {

    }

    /*public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }*/
}