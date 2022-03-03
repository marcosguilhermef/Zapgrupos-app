package com.origin.zapgrupos.ui.AdicionarGrupo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.origin.zapgrupos.R;
import com.origin.zapgrupos.databinding.FragmentAdicionarGrupoBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdicionarGrupoFragment extends Fragment {

    private AdicionarGrupoViewModel adicionarGrupoViewModel;
    private FragmentAdicionarGrupoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        adicionarGrupoViewModel = new ViewModelProvider(this).get(AdicionarGrupoViewModel.class);

        binding = FragmentAdicionarGrupoBinding.inflate(inflater, container, false);
        String[] paises = {"Brasil", "Portugal"};
        List<String> opcoes = Arrays.asList(paises);
        //binding.MenuPaisContent.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.list_item_pais,opcoes));

        View root = binding.getRoot();

        //final TextView textView = binding.textGallery;

        /*adicionarGrupoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}