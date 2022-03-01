package com.origin.zapgrupos.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.origin.zapgrupos.Models.Categorias.CategoriaDataModel;
import com.origin.zapgrupos.Models.Categorias.CategoriaModel;
import com.origin.zapgrupos.Models.Categorias.Repository.Requests;
import com.origin.zapgrupos.databinding.FragmentHomeBinding;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ListView listView;
    final private String url = "https://zapgrupos.xyz/api/mais";
    private Requests Request;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        makeRequest();

        //final TextView textView = binding.textHome;
        listView = binding.listView;
        final ProgressBar progressBar = binding.progressBar;
        homeViewModel.getDownloaded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean b) {
                progressBar.setVisibility( !b ? progressBar.VISIBLE : progressBar.GONE);
            }
        });

        homeViewModel.getCategoria().observe(getViewLifecycleOwner(), new Observer<CategoriaModel>() {
            @Override
            public void onChanged(@Nullable CategoriaModel categoriaModel) {
                MontarListaDeCategorias(categoriaModel);
            }
        }
        );

        listView.setOnItemClickListener(this.onClickItemCategory());

        return root;
    }
    private void MontarListaDeCategorias(CategoriaModel categoriaModel){
        listView = binding.listView;
        homeViewModel.setDownloaded(true);
        List<CategoriaDataModel> ListaDecategorias = categoriaModel.getData();
        ArrayAdapter<CategoriaDataModel> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,ListaDecategorias);
        listView.setAdapter(new ListaDeCategorias(getActivity(),ListaDecategorias));
    }

    private AdapterView.OnItemClickListener onClickItemCategory(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                CategoriaDataModel cat = (CategoriaDataModel) o;
                Toast.makeText(getContext(), "Selected :" + " " + cat.getCategoria(), Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void makeRequest(){
        homeViewModel.setCategoria(getCategoriaModelLiveData());
        homeViewModel.getCategoria();
    }

    private MutableLiveData<CategoriaModel> getCategoriaModelLiveData(){
        if(homeViewModel.getCategoria() == null){
            try{
                URL url = new URL(this.url);
                Request = new Requests(url,getActivity().getApplicationContext(), CategoriaModel.class);
                return Request.rum().getResponseLiveData();
            }catch (MalformedURLException e) {
                return null;
            }
        }
        return homeViewModel.getCategoria();
    }

}