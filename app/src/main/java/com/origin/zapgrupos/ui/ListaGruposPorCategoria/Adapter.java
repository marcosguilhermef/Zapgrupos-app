package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.origin.zapgrupos.R;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;
import org.jetbrains.annotations.NotNull;
import com.origin.zapgrupos.databinding.ListItemGrupoBinding;
/*
    This adapter will handle listing of movies in recyclerview
 */
public class Adapter extends PagingDataAdapter<Grupo, Adapter.AdapterViewHolder> {
    // Define Loading ViewType
    public static final int LOADING_ITEM = 0;
    // Define Movie ViewType
    public static final int MOVIE_ITEM = 1;
    public final Context context;
    Activity activity;


    public Adapter(@NotNull DiffUtil.ItemCallback<Grupo> diffCallback, Context context, Activity ac) {
        super(diffCallback);
        this.context = context;
        activity = ac;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterViewHolder(ListItemGrupoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Grupo grupo = getItem(position);

        holder.adapterItemBind.getRoot().setOnClickListener( (v) ->{
            Bundle bundle = new Bundle();
            bundle.putString("_id",grupo.get_id());
            bundle.putString("titulo",grupo.getTitulo());
            bundle.putString("url",grupo.getUrl());
            bundle.putString("img",extractImageURL(grupo));
            bundle.putString("categoria",grupo.getCategoria());
            bundle.putString("descricao",grupo.getDescricao());
            bundle.putBoolean("sensivel",grupo.getSensivel());
            Navigation.findNavController(v).navigate(R.id.nav_grupo, bundle);
        });

        if(isSensible(grupo)){
            configurarCardGrupo(holder.adapterItemBind, grupo);
        }
    }

    private void configurarCardGrupo(ListItemGrupoBinding cardGrupo, Grupo grupo){
        final String url = extractImageURL(grupo);
            if(grupo.getTitulo() != null){
                cardGrupo.textViewTituloGrupo.setText(grupo.getTitulo());
            }else{
                cardGrupo.textViewTituloGrupo.setText(" ");
            }

            if(grupo.getDescricao() != null){
                cardGrupo.textViewDescricaoGrupo.setText(grupo.getDescricao());
            }

            if(grupo.getTipo().equals("whatsapp")){
                cardGrupo.imageViewTipo.setImageResource(R.drawable.icons8_whatsapp_48);
            }

            if(grupo.getTipo().equals("telegram") || grupo.getTipo().equals("t.me")){
                cardGrupo.imageViewTipo.setImageResource(R.drawable.icons8_aplica__o_telegrama_48);
            }

            Glide.with(context)
                        .load(url)
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_error_outline_24)
                        .into(cardGrupo.imageViewGrupo);
    }

    private Boolean isSensible(Grupo grupo){
        Boolean exibir = (grupo.getSensivel() == false) || (grupo.getSensivel() == null);
        return exibir;
    }

    private Boolean isEmpety(Grupo grupo){
       return grupo == null;
    }

    private String extractImageURL(Grupo grupo){
        return grupo.getImg() != null ? grupo.getImg().get(0) : null;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() ? MOVIE_ITEM : LOADING_ITEM;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        ListItemGrupoBinding adapterItemBind;

        public AdapterViewHolder(@NonNull ListItemGrupoBinding adapterItemBind) {
            super(adapterItemBind.getRoot());
            this.adapterItemBind = adapterItemBind;
        }

    }
}