package com.origin.zapgrupos.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.origin.zapgrupos.models.Categorias.CategoriaDataModel;
import com.origin.zapgrupos.R;

import java.util.List;

public class ListaDeCategorias  extends BaseAdapter {

    private List<CategoriaDataModel> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private ViewHolder holder;

    public ListaDeCategorias(Context aContext,  List<CategoriaDataModel> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            
            convertView = layoutInflater.inflate(R.layout.list_item_categoria, null);
            holder = new ViewHolder();
            holder.categoriaView = (ImageView) convertView.findViewById(R.id.imageView_categoria);
            holder.TituloCategoriaView = (TextView) convertView.findViewById(R.id.text_view_titulo_categoria);
            holder.DescricacaoView = (TextView) convertView.findViewById(R.id.text_view_descricao_categoria);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
    
        CategoriaDataModel categoria = this.listData.get(position);
        holder.TituloCategoriaView.setText(categoria.getCategoria());
        holder.DescricacaoView.setText(categoria.getDescricao());
        holder.categoriaView.setTag(categoria.getID());

        try{
            holder.url = categoria.getImg()[0];
            holder.id = categoria.getID();
            Glide.with(context)
                    .load(holder.url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_error_outline_24)
                    .into(holder.categoriaView);
        }catch (Exception e){
            holder.categoriaView.setImageResource(R.drawable.ic_baseline_error_outline_24);
        }

        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier("ic_launcher_foreground" , "drawable", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        ImageView categoriaView;
        TextView TituloCategoriaView;
        TextView DescricacaoView;
        String url;
        String id;
        Bitmap bitmap;
    }

}

