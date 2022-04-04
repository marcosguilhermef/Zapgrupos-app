package com.origin.zapgrupos;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.ListaDeGrupos;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Repository2.Service;
import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Repository2.ZapGruposService;

import org.junit.AfterClass;
import org.junit.Test;

import io.reactivex.rxjava3.core.Observable;

public class TestarPaging{
    private Service serviceClass;
    @AfterClass
    public void setup(){
        serviceClass = new Service("Amizade", 1);
    }
    @Test
    public void TestListener(){
        ZapGruposService listener = serviceClass.servi
    }
}
