package com.origin.zapgrupos.ui.GerarLink;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.origin.zapgrupos.R;
import com.origin.zapgrupos.databinding.FragmentGerarLinkBinding;

public class GerarLinkFragment extends Fragment {

    private FragmentGerarLinkBinding binding;

    public GerarLinkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGerarLinkBinding.inflate(inflater, container, false);
        binding.compartilharLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero = binding.numeroImput.getText().toString();
                String text   = binding.textoImput.getText().toString();
                if(text.length() <= 300) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, String.format(
                            "https://api.whatsapp.com/send?text=%s&phone=%s", text, numero));
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                }
            }
        });
        View view = binding.getRoot();
        return view;
    }
}