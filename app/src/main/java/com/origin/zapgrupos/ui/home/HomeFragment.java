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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.origin.zapgrupos.models.Categorias.CategoriaDataModel;
import com.origin.zapgrupos.models.Categorias.CategoriaModel;
import com.origin.zapgrupos.R;
import com.origin.zapgrupos.databinding.FragmentHomeBinding;
import com.origin.zapgrupos.models.Error.ErrorMensage;
import com.origin.zapgrupos.repository.Services;


import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ListView listView;
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


        homeViewModel.getCategoria().observe(getViewLifecycleOwner(), new Observer<CategoriaModel>() {
            @Override
            public void onChanged(@Nullable CategoriaModel categoriaModel) {
                if(categoriaModel != null){
                    MontarListaDeCategorias(categoriaModel);
                    progressBar.setVisibility(progressBar.GONE);
                    binding.includeError.constraintError.setVisibility(binding.includeError.constraintError.INVISIBLE);
                }
            }
        }
        );

        homeViewModel.getErro().observe(getViewLifecycleOwner(), new Observer<ErrorMensage>() {
            @Override
            public void onChanged(ErrorMensage errorMensage) {
                if(errorMensage != null){
                    listView.setVisibility(listView.INVISIBLE);
                    progressBar.setVisibility(progressBar.GONE);
                    binding.includeError.constraintError.setVisibility(binding.includeError.constraintError.VISIBLE);
                    TextView t = (TextView) binding.includeError.constraintError.findViewById(R.id.textView2);
                    t.setText(R.string.net_work_error);
                }
            }
        });
        listView.setOnItemClickListener(this.onClickItemCategory());

        return root;
    }

    private void makeRequest(){
        Services request = new Services();
        request.getCategories(homeViewModel.getCategoria(), homeViewModel.getErro());
    }


    private void MontarListaDeCategorias(CategoriaModel categoriaModel){
        listView = binding.listView;
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