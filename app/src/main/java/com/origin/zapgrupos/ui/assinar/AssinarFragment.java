package com.origin.zapgrupos.ui.assinar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.origin.zapgrupos.R;
import com.origin.zapgrupos.databinding.FragmentAssinarBinding;
import com.origin.zapgrupos.until.ads.googleBilling.Billing;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssinarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssinarFragment extends Fragment {

    FragmentAssinarBinding databiding;
    public AssinarFragment() {
        // Required empty public constructor
    }

    public static AssinarFragment newInstance(String param1, String param2) {
        AssinarFragment fragment = new AssinarFragment();
        Bundle args = new Bundle();
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        databiding = FragmentAssinarBinding.inflate( inflater, container, false );

        if(!Billing.hasAds()){
            assigned();
        }
        else if( !Billing.DoesBillingisWorking() ){
            databiding.billingNotWorking.setVisibility( View.VISIBLE );
        }
        else{
            databiding.options.setVisibility( View.VISIBLE );
            databiding.semana1.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Billing.loadingProducts("1_semana");
                }
            } );

            databiding.mes1.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Billing.loadingProducts("1_mes");
                }
            } );

            databiding.mes3.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Billing.loadingProducts("3_meses");
                }
            } );
        }



        return databiding.getRoot();
    }

    private void assigned(){
        databiding.options.setVisibility( View.INVISIBLE );
        databiding.optionsAssigned.setVisibility( View.VISIBLE );
        if(Billing.getPurchase() != null){
            databiding.gpacode.setText( Billing.getPurchase().getOrderId() );
            databiding.dataAssinatura.setText( convertDate(Billing.getPurchase().getPurchaseTime()) );
            databiding.infoAssinatura.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                    String.format("https://play.google.com/store/account/subscriptions?package=%s" ,Billing.getPurchase().getPackageName())
                            )
                    );
                    startActivity(browserIntent);

                }
            } );
        }

    }

    private String convertDate(Long date){
        Timestamp t = new Timestamp(date); //for current time in milliseconds
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = new Date(t.getTime());
        return "Data da assinatura: "+dt.format( d );
    }

}