package com.origin.zapgrupos.ui.AdicionarGrupo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.origin.zapgrupos.databinding.FragmentAdicionarGrupoBinding;

public class AdicionarGrupoFragment extends Fragment {

    private AdicionarGrupoViewModel adicionarGrupoViewModel;
    private FragmentAdicionarGrupoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        adicionarGrupoViewModel = new ViewModelProvider(this).get(AdicionarGrupoViewModel.class);

        binding = FragmentAdicionarGrupoBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        final TextView textView = binding.textGallery;

        adicionarGrupoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}