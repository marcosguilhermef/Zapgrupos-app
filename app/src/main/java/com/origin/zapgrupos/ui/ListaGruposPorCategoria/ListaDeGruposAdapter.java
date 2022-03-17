package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;
import com.origin.zapgrupos.R;

import java.util.List;

public class ListaDeGruposAdapter extends BaseAdapter {

    private List<Grupo> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private viewHolder holder;


    public ListaDeGruposAdapter(Context aContext,  List<Grupo> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.list_item_grupo, null);
            holder = new viewHolder();

            holder.imagem = (ImageView) convertView.findViewById(R.id.imageView_grupo);
            holder.titulo = (TextView) convertView.findViewById(R.id.text_view_titulo_grupo);
            holder.descricao = (TextView) convertView.findViewById(R.id.text_view_descricao_grupo);
            holder.imageViewTipo = (ImageView) convertView.findViewById(R.id.imageView_tipo);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }


        Grupo grupo = this.listData.get(position);

        holder.titulo.setText(grupo.getTitulo() != null ? grupo.getTitulo() : "");
        holder.descricao.setText(grupo.getDescricao() != null ? grupo.getDescricao() : "");


        if(grupo.getTipo().equals("whatsapp")){
            holder.imageViewTipo.setImageResource(R.drawable.icons8_whatsapp_48);
        }else if(grupo.getTipo().equals("telegram")){
            holder.imageViewTipo.setImageResource(R.drawable.icons8_aplica__o_telegrama_48);
        }

        try{
            holder.url = grupo.getImg().get(0);
            holder.id = grupo.get_id();
            if((grupo.getSensivel() == false) || (grupo.getSensivel() == null)) {
                Glide.with(context)
                        .load(holder.url)
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_error_outline_24)
                        .into(holder.imagem);
            }else{
                holder.imagem.setImageResource(R.drawable.ic_baseline_error_outline_24);
            }
        }catch (Exception e){
            holder.imagem.setImageResource(R.drawable.ic_baseline_error_outline_24);
        }

        return convertView;
    }

    static class viewHolder {
        String id;
        String url;
        TextView titulo;
        TextView descricao;
        ImageView imagem;
        ImageView imageViewTipo;
    }
}
