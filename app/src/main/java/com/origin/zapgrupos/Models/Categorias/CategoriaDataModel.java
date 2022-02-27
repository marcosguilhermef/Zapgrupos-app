package com.origin.zapgrupos.Models.Categorias;

public class CategoriaDataModel{
    public String _id;
    public String categoria;
    public String[] img;
    public String created_at;
    public String descricao;

    public CategoriaDataModel(){

    }

    public String getID(){
        return _id;
    }
    
    public String getCategoria(){
        return categoria;
    }
    
    public String getCreatedAt(){
        return created_at;
    }
    
    public String[] getImg(){
        return img;
    }
    
    public String getDescricao(){
        return descricao;
    }
}
