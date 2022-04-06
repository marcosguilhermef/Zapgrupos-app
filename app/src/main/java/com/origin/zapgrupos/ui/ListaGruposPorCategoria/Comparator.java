package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.origin.zapgrupos.models.ListaDeGruposPorCategoria.Grupo;

public class Comparator extends DiffUtil.ItemCallback<Grupo> {
    @Override
    public boolean areItemsTheSame(@NonNull Grupo oldItem, @NonNull Grupo newItem) {
        return oldItem.get_id().equals(newItem.get_id());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Grupo oldItem, @NonNull Grupo newItem) {
        return oldItem.get_id().equals(newItem.get_id());
    }
}
