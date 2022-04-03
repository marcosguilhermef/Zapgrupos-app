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
import androidx.navigation.Navigation;

import com.origin.zapgrupos.models.Categorias.CategoriaDataModel;
import com.origin.zapgrupos.models.Categorias.CategoriaModel;
import com.origin.zapgrupos.models.Categorias.Repository.Requests;
import com.origin.zapgrupos.R;
import com.origin.zapgrupos.databinding.FragmentHomeBinding;

import java.net.MalformedURLException;
import java.net.URL;

import android.widget.Toast;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ListView listView;
    final private String url = "https://c385-170-82-181-108.ngrok.io/api/mais";
    private Requests Request;
    private Bundle bundle;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState == null){
            bundle = new Bundle();
        }

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

    private void makeRequest(){
        homeViewModel.setCategoria(getCategoriaModelLiveData());
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

    private void MontarListaDeCategorias(CategoriaModel categoriaModel){
        listView = binding.listView;
        homeViewModel.setDownloaded(true);
        //ArrayAdapter<CategoriaDataModel> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,categoriaModel.getData());
        listView.setAdapter(new ListaDeCategorias(getActivity(),categoriaModel.getData()));
    }

    private AdapterView.OnItemClickListener onClickItemCategory(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                CategoriaDataModel cat = (CategoriaDataModel) o;
                Toast.makeText(getContext(), "Categoria:" + " " + cat.getCategoria(), Toast.LENGTH_LONG).show();
                bundle.putString("categoria",cat.getCategoria());
                Navigation.findNavController(v).navigate(R.id.nav_lista_grupos_por_categoria, bundle);
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}