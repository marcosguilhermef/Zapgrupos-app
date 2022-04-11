package com.origin.zapgrupos.ui.MensagemSemContato;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.origin.zapgrupos.R;
import com.origin.zapgrupos.databinding.FragmentMenagemSemContatoBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenagemSemContatoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenagemSemContatoFragment extends Fragment {

    private FragmentMenagemSemContatoBinding binding;
    private AdView adView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenagemSemContatoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenagemSemContatoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenagemSemContatoFragment newInstance(String param1, String param2) {
        MenagemSemContatoFragment fragment = new MenagemSemContatoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMenagemSemContatoBinding.inflate(inflater, container, false);

        binding.abrirContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String numero = binding.numeroImput.getText().toString();
                    Intent viewIntent = new Intent(
                            "android.intent.action.VIEW",
                            Uri.parse(String.format("https://wa.me/%s", numero)));
                    viewIntent.setPackage("com.whatsapp");
                    startActivity(viewIntent);
                }catch (ActivityNotFoundException e){
                    new MaterialAlertDialogBuilder(getContext())
                            .setMessage("O whatsaap não está instalado. Você deseja baixa-lo?")
                            .setNegativeButton("Não", (dialog, i) -> {
                                dialog.cancel();
                            })
                            .setPositiveButton("Sim",(dialog, i) -> {
                                        Intent viewIntent = new Intent(
                                                "android.intent.action.VIEW",
                                                Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"));
                                        startActivity(viewIntent);
                                    }
                            )
                            .show();
                }

            }
        });

        View view = binding.getRoot();
        return view;
    }

    public void onViewCreated(View view, Bundle bundle){
        super.onViewCreated(view,bundle);
        adView = getActivity().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        adView.destroy();

    }
}