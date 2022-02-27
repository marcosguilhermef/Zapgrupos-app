package com.origin.zapgrupos.ui.AdicionarGrupo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdicionarGrupoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AdicionarGrupoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}