package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;



import com.origin.zapgrupos.databinding.FragmentListaGruposPorCategoriaBinding;


public class ListaGruposPorCategoria extends Fragment {

    private FragmentListaGruposPorCategoriaBinding binding;
    final private String url = "https://zapgrupos.xyz/api/mais";
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListaGruposPorCategoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}