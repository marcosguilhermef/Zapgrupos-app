package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                Log.i("tentando request:", url.toString());
                Request = new Requests(urlFormated,getActivity().getApplicationContext(), ListaDeGrupos.class);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        viewModel.setCategoria(null);
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