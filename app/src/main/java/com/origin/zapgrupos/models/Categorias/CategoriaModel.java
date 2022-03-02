package com.origin.zapgrupos.models.Categorias;
import java.net.URL;
import java.util.List;

public class CategoriaModel{

    public Integer current_page;
    public URL first_page_url;
    public URL last_page_url;
    public URL next_page_url;
    public URL prev_page_url;
    public URL path;
    public Integer last_page;
    public String link;
    public Integer per_page;
    public Integer to;
    public Integer total;
    public List<CategoriaDataModel> data;
    public CategoriaModel(){

    }
    public Integer getCurrentPage(){
        return current_page;
    }

    public URL getFirstPageUrl(){
        return first_page_url;
    }

    public URL getLastPageUrl(){
        return last_page_url;
    }

    public URL getNextPageUrl(){ return next_page_url; }

    public URL getPrevPageUrl(){
        return prev_page_url;
    }

    public URL getPath(){
        return path;
    }

    public List<CategoriaDataModel> getData(){
        return data;
    }

    public String getName(){
        return "CategoriaModel";
    }

}
