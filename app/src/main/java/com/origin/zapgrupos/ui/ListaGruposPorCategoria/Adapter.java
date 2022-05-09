package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import android.content.Context;
import android.os.Bundle;
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

    public Adapter(@NotNull DiffUtil.ItemCallback<Grupo> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterViewHolder(ListItemGrupoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Grupo currenntGrupo = getItem(position);
        holder.adapterItemBind.getRoot().setOnClickListener( (v) ->{
            Object o = getItem(position);
            Grupo group = (Grupo) o;
            Bundle bundle = new Bundle();
            bundle.putString("_id",group.get_id());
            bundle.putString("titulo",group.getTitulo());
            bundle.putString("url",group.getUrl());
            bundle.putString("img",group.getImg() != null ? group.getImg().get(0) : null);
            bundle.putString("categoria",group.getCategoria());
            bundle.putString("descricao",group.getDescricao());
            bundle.putBoolean("sensivel",group.getSensivel());
            Navigation.findNavController(v).navigate(R.id.nav_grupo, bundle);
        });
        holder.adapterItemBind.getRoot().setClickable(true);
        holder.adapterItemBind.getRoot().setLongClickable(true);

        if (currenntGrupo != null) {

            if(currenntGrupo.getTitulo() != null){
                holder.adapterItemBind.textViewTituloGrupo.setText(currenntGrupo.getTitulo());
            }

            if(currenntGrupo.getDescricao() != null){
                holder.adapterItemBind.textViewDescricaoGrupo.setText(currenntGrupo.getDescricao());
            }

            if(currenntGrupo.getTipo().equals("whatsapp")){
                holder.adapterItemBind.imageViewTipo.setImageResource(R.drawable.icons8_whatsapp_48);
            }

            if(currenntGrupo.getTipo().equals("telegram") || currenntGrupo.getTipo().equals("t.me")){
                holder.adapterItemBind.imageViewTipo.setImageResource(R.drawable.icons8_aplica__o_telegrama_48);
            }

            String url = currenntGrupo.getImg() != null ? currenntGrupo.getImg().get(0) : null;
            Boolean exibir = (currenntGrupo.getSensivel() == false) || (currenntGrupo.getSensivel() == null);

            if(exibir) {
                Glide.with(context)
                        .load(url)
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_error_outline_24)
                        .into(holder.adapterItemBind.imageViewGrupo);
            }else{
                Glide.with(context)
                        .load(R.drawable.ic_baseline_error_outline_24)
                        .centerCrop()
                        .into(holder.adapterItemBind.imageViewGrupo);
            }
        }
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