package com.origin.zapgrupos.ui.home;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import  com.origin.zapgrupos.MainActivity;
import com.origin.zapgrupos.Models.Categorias.CategoriaDataModel;
import com.origin.zapgrupos.R;

class ImgTag{
    public String tag;
    public Bitmap img;
    public ImgTag(String tag, Bitmap img){
        this.tag = tag;
        this.img = img;
    }

}

public class DownloadImagens extends AsyncTask<ListaDeCategorias.ViewHolder, Void, ListaDeCategorias.ViewHolder> {
    private ListaDeCategorias.ViewHolder binding;

    class Request {
        public Bitmap download(String url, String tag) throws IOException {
            URL endereco;
            InputStream inputStream;
            Bitmap imagem;
            endereco = new URL(url);
            inputStream = endereco.openStream();
            imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return imagem;
        }
    }

    @Override
    protected void onPreExecute(){
        Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " + Thread.currentThread().getName());
    }

    @Override
    protected ListaDeCategorias.ViewHolder doInBackground(ListaDeCategorias.ViewHolder... params) {
        ListaDeCategorias.ViewHolder viewHolder = params[0];
        Request r = new Request();
        try {
            URL endereco = new URL(params[0].url);
            InputStream inputStream = endereco.openStream();
            viewHolder.bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            viewHolder.bitmap = null;
        }
        return viewHolder;
    }

    protected void onPostExecute(ListaDeCategorias.ViewHolder result){
        Log.i("onPostExecute", result.id);
        if(result.bitmap != null){
            Log.i("onPostExecute", result.id);
            result.categoriaView.setImageBitmap(result.bitmap);
        }else{
            result.categoriaView.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    protected void onProgressUpdate(Integer... params){
        //Codigo
    }
}