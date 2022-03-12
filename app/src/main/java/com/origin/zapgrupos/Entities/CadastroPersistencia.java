package com.origin.zapgrupos.Entities;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class CadastroPersistencia {
    @PrimaryKey
    public int id;
    public String email;
    public String telefone;
}
